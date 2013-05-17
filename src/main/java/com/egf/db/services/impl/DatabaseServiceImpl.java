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
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		CommentImpl commentImpl=(CommentImpl)comment;
		String tn=tableImpl.getName();
		TableImpl tmi=new TableImpl();
		//进行回调操作
		callback.doCreateAction(tmi);
		StringBuffer columns=tmi.columns;
		columns=columns.delete(columns.length()-2, columns.length());
		String sql=String.format("create table %s (\n%s\n);", tn,columns.toString());
		String tableComment=String.format("comment on table %s is '%s';\n", tn,commentImpl.getComment());
		String commentsSql=tmi.comments.toString();
		commentsSql=commentsSql.replaceAll("TN", tn);
		logger.info("\n"+sql+"\n"+tableComment+commentsSql.toString());
		jdbcService.execute(sql+"\n"+tableComment+commentsSql.toString());
	}
	
	public void createTable(TableName tableName, CreateTableCallback callback) throws SQLException{
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		String tn=tableImpl.getName();
		TableImpl tmi=new TableImpl();
		//进行回调操作
		callback.doCreateAction(tmi);
		StringBuffer columns=tmi.columns;
		columns=columns.delete(columns.length()-2, columns.length());
		String sql=String.format("create table %s (\n%s\n);", tn,columns.toString());
		String commentsSql=tmi.comments.toString();
		commentsSql=commentsSql.replaceAll("TN", tn);
		logger.info("\n"+sql+"\n"+commentsSql.toString());
		jdbcService.execute(sql+"\n"+commentsSql.toString());
	}
	
	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType) throws SQLException{
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String type=columnType.getColumnType();
		String sql=generate.AddColumn(tn, cn, type);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	
	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, NullOrNotNull nullOrNotNull) throws SQLException{
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String type=columnType.getColumnType();
		String nullorNot=nullOrNotNull.out();
		String sql=generate.AddColumn(tn, cn, type,nullorNot);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Comment comment) throws SQLException{
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		CommentImpl commentImpl=(CommentImpl)comment;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String type=columnType.getColumnType();
		String c=commentImpl.getComment();
		String sql=generate.AddColumn(tn, cn, type);
		String csql=generate.addComment(tn, cn, c);
		logger.info("\n"+sql+"\n"+csql);
		jdbcService.execute(sql+"\n"+csql);
	}
	
	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, NullOrNotNull nullOrNotNull, Comment comment) throws SQLException{
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		CommentImpl commentImpl=(CommentImpl)comment;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String type=columnType.getColumnType();
		String nullOrNot=nullOrNotNull.out();
		String c=commentImpl.getComment();
		String sql=generate.AddColumn(tn, cn, type,nullOrNot);
		String csql=generate.addComment(tn, cn, c);
		logger.info("\n"+sql+"\n"+csql);
		jdbcService.execute(sql+"\n"+csql);
	}

	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Default deft) throws SQLException{
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		DefaultImpl deftImpl=(DefaultImpl)deft;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String type=columnType.getColumnType();
		String value=deftImpl.getValue();
		String sql=generate.AddColumn(tn, cn, type);
		String dsql=generate.addDefault(tn, cn, value);
		logger.info("\n"+sql+"\n"+dsql);
		jdbcService.execute(sql+"\n"+dsql);
	}

	
	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Default deft, NullOrNotNull nullOrNotNull) throws SQLException{
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		DefaultImpl deftImpl=(DefaultImpl)deft;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String type=columnType.getColumnType();
		String nullOrNot=nullOrNotNull.out();
		String value=deftImpl.getValue();
		String sql=generate.AddColumn(tn, cn, type,nullOrNot);
		String dsql=generate.addDefault(tn, cn, value);
		logger.info("\n"+sql+"\n"+dsql);
		jdbcService.execute(sql+"\n"+dsql);
	}


	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Default deft, Comment comment) throws SQLException{
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		CommentImpl commentImpl=(CommentImpl)comment;
		DefaultImpl deftImpl=(DefaultImpl)deft;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String c=commentImpl.getComment();
		String type=columnType.getColumnType();
		String value=deftImpl.getValue();
		String sql=generate.AddColumn(tn, cn, type);
		String dsql=generate.addDefault(tn, cn, value);
		String csql=generate.addComment(tn, cn, c);
		logger.info("\n"+sql+"\n"+dsql+"\n"+csql);
		jdbcService.execute(sql+"\n"+dsql+"\n"+csql);
	}


	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Default deft, NullOrNotNull nullOrNotNull,Comment comment) throws SQLException{
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		CommentImpl commentImpl=(CommentImpl)comment;
		DefaultImpl deftImpl=(DefaultImpl)deft;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String c=commentImpl.getComment();
		String type=columnType.getColumnType();
		String nullOrNot=nullOrNotNull.out();
		String value=deftImpl.getValue();
		String sql=generate.AddColumn(tn, cn, type,nullOrNot);
		String dsql=generate.addDefault(tn, cn, value);
		String csql=generate.addComment(tn, cn, c);
		logger.info("\n"+sql+"\n"+dsql+"\n"+csql);
		jdbcService.execute(sql+"\n"+dsql+"\n"+csql);
	}
	
	public void addColumnNotNull(TableName tableName, ColumnName columnName) throws SQLException{
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String columnType=jdbcService.getColumnTypeName(tn, cn);
		String sql=generate.addColumnNullOrNot(tn, cn,columnType, NOT_NULL);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void addColumnNull(TableName tableName, ColumnName columnName) throws SQLException{
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String sql=generate.addColumnNull(tn, cn, NULL);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void addDefault(TableName tableName, ColumnName columnName,Default deft) throws SQLException{
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		DefaultImpl deftImpl=(DefaultImpl)deft;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String value=deftImpl.getValue();
		String sql=generate.addDefault(tn, cn, value);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	
	public void updateDefault(TableName tableName, ColumnName columnName,Default deft) throws SQLException{
		addDefault(tableName, columnName, deft);
	}
	
	public void addIndex(TableName tableName, IndexName IndexName,ColumnName... columnName) throws SQLException{
		String[] columnNames=new String[columnName.length];
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		IndexNameImpl indexImpl=(IndexNameImpl)IndexName;
		String tn=tableImpl.getName();
		String in=indexImpl.getName();
		for (int i=0;i<columnName.length;i++) {
			ColumnNameImpl columnImpl=(ColumnNameImpl)columnName[i];
			columnNames[i]=columnImpl.getName();
		}
		GenerateImpl gi=new GenerateImpl();
		String sql=gi.addIndex(tn, in, columnNames);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void addIndex(TableName tableName, IndexName IndexName, IndexType indexType,ColumnName... columnName) throws SQLException{
		String[] columnNames=new String[columnName.length];
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		IndexNameImpl indexImpl=(IndexNameImpl)IndexName;
		String tn=tableImpl.getName();
		String in=indexImpl.getName();
		String type=indexType.getIndexType();
		for (int i=0;i<columnName.length;i++) {
			ColumnNameImpl columnImpl=(ColumnNameImpl)columnName[i];
			columnNames[i]=columnImpl.getName();
		}
		String sql=generate.addIndex(tn,in,type,columnNames);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void addPrimaryKey(String name, TableName tableName,ColumnName... columnName) throws SQLException{
		String sql=addKey(name, tableName, PRIMARY_KEY, columnName);
		//修正主键不能为空
		for (ColumnName cn : columnName) {
			this.addColumnNotNull(tableName, cn);
		}
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}
	
	public void addForeignKey(String name, TableName tableName,ColumnName... columnName) throws SQLException{
		String sql=addKey(name, tableName, FOREIGN_KEY, columnName);
		logger.info(sql);
		jdbcService.execute(sql);
	}

	public void addUnique(String name, TableName tableName, ColumnName... columnName) throws SQLException{
		String sql=addKey(name, tableName, UNIQUE_KEY, columnName);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}

	public void dropTable(String name) throws SQLException{
		String sql=generate.dropTalbe(name);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}
	
	public void dropColumn(TableName tableName,ColumnName columnName) throws SQLException{
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
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
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		CommentImpl commentImpl=(CommentImpl)comment;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String c=commentImpl.getComment();
		String sql=generate.addComment(tn, cn, c);
		logger.info("\n"+sql);
		jdbcService.execute(sql);
	}
	
	
	public void updateComment(TableName tableName, ColumnName columnName,Comment comment) throws SQLException{
		addComment(tableName, columnName, comment);
	}

	private String addKey(String name, TableName tableName,String keyType,ColumnName... columnName){
		String[] columnNames=new String[columnName.length];
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		String tn=tableImpl.getName();
		for (int i=0;i<columnName.length;i++) {
			ColumnNameImpl columnImpl=(ColumnNameImpl)columnName[i];
			columnNames[i]=columnImpl.getName();
		}
		String sql=generate.AddConstraint(tn, name, keyType, columnNames);
		return sql;
	}
	
	private String dropKey(TableName tableName,String name){
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		String tn=tableImpl.getName();
	    String sql=generate.dropConstraint(tn, name);
	    return sql;
	}

}
