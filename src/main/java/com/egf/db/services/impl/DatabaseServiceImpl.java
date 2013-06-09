/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DatabaseServiceImpl.java,fangj Exp$
 * created at:下午01:08:24
 */
package com.egf.db.services.impl;

import org.apache.log4j.Logger;

import com.egf.db.core.CreateTableCallback;
import com.egf.db.core.db.DbFactory;
import com.egf.db.core.db.DbInterface;
import com.egf.db.core.define.ColumnType;
import com.egf.db.core.define.column.ColumnDefine;
import com.egf.db.core.define.column.Comment;
import com.egf.db.core.define.column.Default;
import com.egf.db.core.define.column.NotNull;
import com.egf.db.core.define.column.PrimaryKey;
import com.egf.db.core.define.column.Unique;
import com.egf.db.core.define.name.ColumnName;
import com.egf.db.core.define.name.ForeignKeyName;
import com.egf.db.core.define.name.IndexName;
import com.egf.db.core.define.name.PrimaryKeyName;
import com.egf.db.core.define.name.TableName;
import com.egf.db.core.define.name.UniqueName;
import com.egf.db.core.jdbc.JdbcService;
import com.egf.db.core.jdbc.JdbcServiceImpl;
import com.egf.db.core.sql.template.Generate;
import com.egf.db.core.sql.template.GenerateFactory;
import com.egf.db.exception.MigrationException;
import com.egf.db.services.DatabaseService;
import com.egf.db.utils.StringUtils;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
class DatabaseServiceImpl implements DatabaseService{

	Logger logger=Logger.getLogger(DatabaseServiceImpl.class);
	
	private final String PRIMARY_KEY="primary key";
	
	private final String FOREIGN_KEY="foreign key";
	
	private final String UNIQUE_KEY="unique";
	
	private final String NOT_NULL="not null";
	
	private final static String HANDLE_COLUMN_TYPE_ADD="add";
	
	private final static String HANDLE_COLUMN_TYPE_CHANGE="change";
	
	private JdbcService jdbcService=new JdbcServiceImpl();
	
	private Generate generate=GenerateFactory.getGenerate();
	
	//仅仅用来测试
	void setJdbcService(JdbcService jdbcService){
		this.jdbcService = jdbcService;
	}
	
	public void createTable(TableName tableName, Comment comment,CreateTableCallback createTableCallback)throws MigrationException {
		TableImpl tmi=new TableImpl();
		String tableComment="";
		//进行回调操作
		createTableCallback.doCreateAction(tmi);
		StringBuffer columns=tmi.columns;
		columns=columns.delete(columns.length()-2, columns.length());
		String tn=tableName.getName();
		if(tn.indexOf(".")!=-1){
			//创建用户
			DbInterface di=DbFactory.getDb();
			String schema=tn.split("\\.")[0];
			di.createSchema(schema);
		}
		String sql=String.format("create table %s (\n%s\n);",tableName.getName(),columns.toString());
		if(comment!=null){
			tableComment=GenerateFactory.getGenerate().changeTableComment(tableName.getName(),comment.getComment());
		}
		String commentsSql=tmi.comments.toString();
		commentsSql=commentsSql.replaceAll("TN", tableName.getName());
		logger.info("\n"+sql+"\n"+tableComment+"\n"+commentsSql.toString());
		jdbcService.execute(sql+"\n"+tableComment+"\n"+commentsSql.toString());
	}
	
	public void createTable(TableName tableName, CreateTableCallback createTableCallback) throws MigrationException{
		createTable(tableName, null, createTableCallback);
	}
	
	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, ColumnDefine... define) throws MigrationException{
		String sql=this.handleColumn(HANDLE_COLUMN_TYPE_ADD, tableName, columnName, columnType, define);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}
	
	public void changeColumn(TableName tableName, ColumnName columnName,ColumnDefine... define) throws MigrationException {
		changeColumn(tableName, columnName,null, define);
	}
	
	public void changeColumn(TableName tableName, ColumnName columnName,ColumnType columnType, ColumnDefine... define)throws MigrationException {
		String sql=this.handleColumn(HANDLE_COLUMN_TYPE_CHANGE, tableName, columnName, columnType, define);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}
	
	public void addIndex(TableName tableName, ColumnName... columnName)throws MigrationException {
		addIndex(tableName, null, columnName);
	}

	public void addIndex(TableName tableName, IndexName indexName,ColumnName... columnName) throws MigrationException{
		String[] columnNames=new String[columnName.length];
		String tn=tableName.getName();
		String in=null;
		String sql=null;
		for (int i=0;i<columnName.length;i++) {
			ColumnNameImpl columnImpl=(ColumnNameImpl)columnName[i];
			columnNames[i]=columnImpl.getName();
		}
		if(indexName==null){
			//自动命名
			in=StringUtils.getUnionStringArray(columnNames, "_")+"_index";
			if(in.length()>30){
				for (String s : columnNames) {
					in=in+s.charAt(0)+"_";
				}
				in=in+"index";
			}
		}else{
			in=indexName.getName();
		}
		sql=generate.addIndex(tn, in, columnNames);	
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void addPrimaryKey(PrimaryKeyName primaryKeyName, TableName tableName,ColumnName... columnName) throws MigrationException{
		StringBuffer sb=new StringBuffer();
		//修正主键不能为空
		for (ColumnName cn : columnName) {
			String sql=this.handleColumn(HANDLE_COLUMN_TYPE_CHANGE, tableName, cn, null,new NotNullImpl());
			sb.append(sql+"\n");
		}
		String sql=addKey(primaryKeyName.getName(),tableName,null,PRIMARY_KEY,columnName);
		sb.append(sql+"\n");
		logger.info("\n"+sb.toString());
		jdbcService.execute(sb.toString());
	}
	
	public void addForeignKey(ForeignKeyName foreignKeyName, TableName tableName,TableName refTableName,ColumnName... columnName) throws MigrationException{
		String sql=addKey(foreignKeyName.getName(), tableName,refTableName,FOREIGN_KEY, columnName);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void addUnique(UniqueName uniqueName, TableName tableName, ColumnName... columnName) throws MigrationException{
		String sql=addKey(uniqueName.getName(), tableName,null,UNIQUE_KEY, columnName);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void dropTable(TableName tableName) throws MigrationException{
		String sql=generate.dropTalbe(tableName.getName());
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}
	
	public void dropColumn(TableName tableName,ColumnName columnName) throws MigrationException{
		String tn=tableName.getName();
		String cn=columnName.getName();
		String sql=generate.dropColumn(tn, cn);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void dropIndex(IndexName indexName) throws MigrationException{
	    String sql=	generate.dropIndex(indexName.getName());
	    logger.info("\n"+sql);
	    jdbcService.execute(sql);
	}
	
	public void dropForeignKey(TableName tableName,ForeignKeyName foreignKeyName) throws MigrationException{
		String sql=generate.dropForeignKey(tableName.getName(), foreignKeyName.getName());
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void dropPrimaryKey(TableName tableName) throws MigrationException{
		//查询表对应的主键名称
		DbInterface di=DbFactory.getDb();
		String pkName=di.getPrimaryKeyName(tableName.getName());
		String sql=generate.dropPrimaryKey(tableName.getName(), pkName);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void dropUnique(TableName tableName,UniqueName uniqueName) throws MigrationException{
		String sql=generate.dropUnique(tableName.getName(), uniqueName.getName());
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void renameColumn(TableName tableName, ColumnName oldColumnName,ColumnName newColumnName) {
		DbInterface di=DbFactory.getDb();
		String type=di.getColumnType(tableName.getName(), oldColumnName.getName());
		String sql=generate.renameColumnName(tableName.getName(), oldColumnName.getName(), newColumnName.getName(),type);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}
	
	/**
	 * 处理列(添加、修改)
	 * @param handleType 处理类型(add,change)
	 * @param tableName 表名称
	 * @param columnName 列名称
	 * @param columnType 列类型
	 * @param define 列定义
	 */
	private String handleColumn(String handleType,TableName tableName, ColumnName columnName,ColumnType columnType, ColumnDefine... define){
		Default deft=null;
		NotNull notNull=null;
		Comment comment=null;
		Unique unique=null;
		String sql=null;
		PrimaryKey primaryKey=null;
		for (ColumnDefine columnDefine : define) {
			if(columnDefine!=null){
				if(columnDefine instanceof Default){
					deft=(Default)columnDefine;
				}else if(columnDefine instanceof NotNull){
					notNull=(NotNull)columnDefine;
				}else if (columnDefine instanceof Comment){
					comment=(Comment)columnDefine;
				}else if(columnDefine instanceof Unique){
					unique=(Unique)columnDefine;
				}else if(columnDefine instanceof PrimaryKey){
					primaryKey=(PrimaryKey)columnDefine;
				}
			}
		}
		if(HANDLE_COLUMN_TYPE_ADD.equals(handleType)){
			sql=generate.addColumn(tableName.getName(), columnName.getName(), columnType.getColumnType(),notNull==null?null:NOT_NULL, deft==null?null:deft.getValue(), comment==null?null:comment.getComment(), unique==null?null:UNIQUE_KEY, primaryKey==null?null:PRIMARY_KEY);
		}else if(HANDLE_COLUMN_TYPE_CHANGE.equals(handleType)){
				String type=DbFactory.getDb().getColumnType(tableName.getName(), columnName.getName());
				sql= generate.changeColumn(tableName.getName(), columnName.getName(),columnType==null?type:columnType.getColumnType(),notNull==null?null:NOT_NULL, deft==null?null:deft.getValue(), comment==null?null:comment.getComment());
		}
		return sql;
	}
	
	/**
	 * 增加键约束
	 * @param name 名称
	 * @param tableName 表名
	 * @param refTableName 参照表名
	 * @param keyType 键类型
	 * @param columnName 列
	 * @return
	 */
	private String addKey(String name, TableName tableName,TableName refTableName, String keyType,ColumnName... columnName){
		String[] columnNames=new String[columnName.length];
		String sql=null;
		String tn=tableName.getName();
		for (int i=0;i<columnName.length;i++) {
			ColumnNameImpl columnImpl=(ColumnNameImpl)columnName[i];
			columnNames[i]=columnImpl.getName();
		}
		if(refTableName!=null){
			//获取主键对应的列
			DbInterface di=DbFactory.getDb();
			String[] referencesColumns=di.getPrimaryKeyColumn(refTableName.getName());
			String referencesColumn=StringUtils.getUnionStringArray(referencesColumns, ",");
			sql=generate.addForeignKey(tn, name, refTableName.getName(), referencesColumn, columnNames);
		}else{
			sql=generate.addConstraint(tn, name, keyType, columnNames);
		}
		return sql;
	}
	
}
