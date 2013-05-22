/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DatabaseServiceImpl.java,fangj Exp$
 * created at:下午01:08:24
 */
package com.egf.db.services.impl;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.egf.db.core.CreateTableCallback;
import com.egf.db.core.define.ColumnType;
import com.egf.db.core.define.IndexType;
import com.egf.db.core.define.column.ColumnDefine;
import com.egf.db.core.define.column.Comment;
import com.egf.db.core.define.column.Default;
import com.egf.db.core.define.column.NullOrNotNull;
import com.egf.db.core.define.name.ColumnName;
import com.egf.db.core.define.name.IndexName;
import com.egf.db.core.define.name.TableName;
import com.egf.db.core.jdbc.JdbcService;
import com.egf.db.core.jdbc.JdbcServiceImpl;
import com.egf.db.core.sql.template.Generate;
import com.egf.db.services.DatabaseService;

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
	
	private final String NULL="null";
	
	private final String NOT_NULL="not null";
	
	private JdbcService jdbcService=new JdbcServiceImpl();
	
	private Generate generate=new GenerateImpl();
	
	public void createTable(TableName tableName, Comment comment,CreateTableCallback callback)throws SQLException {
		TableImpl tmi=new TableImpl();
		String tableComment=null;
		//进行回调操作
		callback.doCreateAction(tmi);
		StringBuffer columns=tmi.columns;
		columns=columns.delete(columns.length()-2, columns.length());
		String sql=String.format("create table %s (\n%s\n);", tableName.getName(),columns.toString());
		if(comment!=null){
			tableComment=String.format("comment on table %s is '%s';\n", tableName.getName(),comment.getComment());
		}
		String commentsSql=tmi.comments.toString();
		commentsSql=commentsSql.replaceAll("TN", tableName.getName());
		logger.info("\n"+sql+"\n"+tableComment+commentsSql.toString());
		jdbcService.execute(sql+"\n"+tableComment+commentsSql.toString());
	}
	
	public void createTable(TableName tableName, CreateTableCallback callback) throws SQLException{
		createTable(tableName, null, callback);
	}
	
	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType) throws SQLException{
		String tn=tableName.getName();
		String cn=columnName.getName();
		String type=columnType.getColumnType();
		String sql=generate.addColumn(tn, cn, type);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}
	
	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, NullOrNotNull nullOrNotNull) throws SQLException{
		this.addColumn(tableName, columnName, columnType, nullOrNotNull,null, null);
	}

	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Comment comment) throws SQLException{
		this.addColumn(tableName, columnName, columnType, null,null, comment);
	}
	
	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, NullOrNotNull nullOrNotNull, Comment comment) throws SQLException{
		this.addColumn(tableName, columnName, columnType, nullOrNotNull,null, comment);
	}

	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Default deft) throws SQLException{
		this.addColumn(tableName, columnName, columnType, null,deft, null);
	}

	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Default deft, NullOrNotNull nullOrNotNull) throws SQLException{
		this.addColumn(tableName, columnName, columnType, nullOrNotNull,deft, null);
	}

	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Default deft, Comment comment) throws SQLException{
		this.addColumn(tableName, columnName, columnType, null,deft, comment);
	}

	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Default deft, NullOrNotNull nullOrNotNull,Comment comment) throws SQLException{
		this.addColumn(tableName, columnName, columnType);
		if(nullOrNotNull!=null){
			if("not null".equals(nullOrNotNull.out())){
				this.addColumnNotNull(tableName, columnName);
			}else{
				this.addColumnNull(tableName, columnName);
			}
		}if(deft!=null){
			this.addDefault(tableName, columnName, deft);
		}if(comment!=null){
			this.addComment(tableName, columnName, comment);
		}
	}
	
	public void addColumnNotNull(TableName tableName, ColumnName columnName) throws SQLException{
		String tn=tableName.getName();
		String cn=columnName.getName();
		String columnType=jdbcService.getColumnTypeName(tn, cn);
		String sql=generate.addColumnNullOrNot(tn, cn,columnType, NOT_NULL);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void addColumnNull(TableName tableName, ColumnName columnName) throws SQLException{
		String tn=tableName.getName();
		String cn=columnName.getName();
		String sql=generate.addColumnNull(tn, cn, NULL);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, ColumnDefine... define) throws SQLException{
		Default deft=null;
		NullOrNotNull nullOrNotNull=null;
		Comment comment=null;
		for (ColumnDefine columnDefine : define) {
			if(columnDefine instanceof Default){
				deft=(Default)columnDefine;
			}else if(columnDefine instanceof NullOrNotNull){
				nullOrNotNull=(NullOrNotNull)columnDefine;
			}else if (columnDefine instanceof Comment){
				comment=(Comment)columnDefine;
			}
		}
		this.addColumn(tableName, columnName, columnType, deft, nullOrNotNull, comment);
	}
	
	public void addDefault(TableName tableName, ColumnName columnName,Default deft) throws SQLException{
		String tn=tableName.getName();
		String cn=columnName.getName();
		String value=deft.getValue();
		String columnType=jdbcService.getColumnTypeName(tn, cn);
		String sql=generate.addDefault(tn, cn,columnType, value);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	
	public void updateDefault(TableName tableName, ColumnName columnName,Default deft) throws SQLException{
		addDefault(tableName, columnName, deft);
	}
	
	public void addIndex(TableName tableName, IndexName IndexName,ColumnName... columnName) throws SQLException{
		addIndex(tableName, IndexName, null, columnName);
	}

	public void addIndex(TableName tableName, IndexName IndexName, IndexType indexType,ColumnName... columnName) throws SQLException{
		String[] columnNames=new String[columnName.length];
		String tn=tableName.getName();
		String in=IndexName.getName();
		String sql=null;
		for (int i=0;i<columnName.length;i++) {
			ColumnNameImpl columnImpl=(ColumnNameImpl)columnName[i];
			columnNames[i]=columnImpl.getName();
		}
		if(indexType!=null){
			String type=indexType.getIndexType();
			sql=generate.addIndex(tn,in,type,columnNames);
		}else {
			sql=generate.addIndex(tn, in, columnNames);	
		}
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void addPrimaryKey(String name, TableName tableName,ColumnName... columnName) throws SQLException{
		String sql=addKey(name, tableName,null, PRIMARY_KEY, columnName);
		//修正主键不能为空
		for (ColumnName cn : columnName) {
			this.addColumnNotNull(tableName, cn);
		}
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}
	
	public void addForeignKey(String name, TableName tableName,TableName refTableName,ColumnName... columnName) throws SQLException{
		String sql=addKey(name, tableName,refTableName,FOREIGN_KEY, columnName);
		logger.info(sql);
		jdbcService.execute(sql);
	}

	public void addUnique(String name, TableName tableName, ColumnName... columnName) throws SQLException{
		String sql=addKey(name, tableName,null,UNIQUE_KEY, columnName);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void dropTable(String name) throws SQLException{
		String sql=generate.dropTalbe(name);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}
	
	public void dropColumn(TableName tableName,ColumnName columnName) throws SQLException{
		String tn=tableName.getName();
		String cn=columnName.getName();
		String sql=generate.dropColumn(tn, cn);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void dropIndex(String name) throws SQLException{
	    String sql=	generate.dropIndex(name);
	    logger.info("\n"+sql);
	    jdbcService.execute(sql);
	}
	
	public void dropForeignKey(TableName tableName,String name) throws SQLException{
		String sql=dropKey(tableName, name);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void dropPrimaryKey(TableName tableName,String name) throws SQLException{
		String sql=dropKey(tableName, name);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	

	public void dropUnique(TableName tableName,String name) throws SQLException{
		String sql=dropKey(tableName, name);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	
	public void addComment(TableName tableName, ColumnName columnName,Comment comment) throws SQLException{
		String tn=tableName.getName();
		String cn=columnName.getName();
		String c=comment.getComment();
		String sql=generate.addComment(tn, cn, c);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}
	
	
	public void updateComment(TableName tableName, ColumnName columnName,Comment comment) throws SQLException{
		addComment(tableName, columnName, comment);
	}

	private String addKey(String name, TableName tableName,TableName refTableName, String keyType,ColumnName... columnName){
		String[] columnNames=new String[columnName.length];
		String sql=null;
		String tn=tableName.getName();
		for (int i=0;i<columnName.length;i++) {
			ColumnNameImpl columnImpl=(ColumnNameImpl)columnName[i];
			columnNames[i]=columnImpl.getName();
		}
		if(refTableName!=null){
			//查询主键对应的列
			generate.addForeignKey(tn, name, refTableName.getName(), null, columnNames);
		}else{
			sql=generate.addConstraint(tn, name, keyType, columnNames);
		}
		return sql;
	}
	
	private String dropKey(TableName tableName,String name){
		String tn=tableName.getName();
	    String sql=generate.dropConstraint(tn, name);
	    return sql;
	}
	
}
