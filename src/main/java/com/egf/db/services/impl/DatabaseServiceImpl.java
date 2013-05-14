/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DatabaseServiceImpl.java,fangj Exp$
 * created at:下午01:08:24
 */
package com.egf.db.services.impl;

import java.sql.Connection;

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
import com.egf.db.core.jdbc.DBConnectionManager;
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
	
	private JdbcService jdbcService=new JdbcServiceImpl();
	
	private static Connection connection=DBConnectionManager.getConnection();
	
	private Generate generate=new GenerateImpl();
	
	public void createTable(TableName tableName, Comment comment,CreateTableCallback callback) {
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		String tn=tableImpl.getName();
		TableImpl tmi=new TableImpl();
		//进行回调操作
		callback.doCreateAction(tmi);
		
	}
	
	public void createTable(TableName tableName, CreateTableCallback callback) {
		// TODO Auto-generated method stub
		
	}
	
	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType) {
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String type=columnType.getColumnType();
		String sql=generate.AddColumn(tn, cn, type);
		logger.info(sql);
	}

	
	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, NullOrNotNull nullOrNotNull) {
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String type=columnType.getColumnType();
		String nullorNot=nullOrNotNull.out();
		String sql=generate.AddColumn(tn, cn, type,nullorNot);
		logger.info(sql);
	}

	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Comment comment) {
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		CommentImpl commentImpl=(CommentImpl)comment;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String type=columnType.getColumnType();
		String c=commentImpl.getComment();
		String sql=generate.AddColumn(tn, cn, type);
		String csql=generate.addComment(tn, cn, c);
		logger.info(sql+"\n"+csql);
	}
	
	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, NullOrNotNull nullOrNotNull, Comment comment) {
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
		logger.info(sql+"\n"+csql);
	}

	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Default deft) {
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		DefaultImpl deftImpl=(DefaultImpl)deft;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String type=columnType.getColumnType();
		String value=deftImpl.getValue();
		String sql=generate.AddColumn(tn, cn, type);
		String dsql=generate.addDefault(tn, cn, value);
		logger.info(sql+"\n"+dsql);
	}

	
	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Default deft, NullOrNotNull nullOrNotNull) {
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
		logger.info(sql+"\n"+dsql);
		
	}


	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Default deft, Comment comment) {
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
		logger.info(sql+"\n"+csql+"\n"+dsql);
	}


	public void addColumn(TableName tableName, ColumnName columnName,ColumnType columnType, Default deft, NullOrNotNull nullOrNotNull,Comment comment) {
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
		logger.info(sql+"\n"+csql+"\n"+dsql);
		
	}

	public void addDefault(TableName tableName, ColumnName columnName,Default deft) {
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		DefaultImpl deftImpl=(DefaultImpl)deft;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String value=deftImpl.getValue();
		String sql=generate.addDefault(tn, cn, value);
		logger.info(sql);
	}

	
	public void updateDefault(TableName tableName, ColumnName columnName,Default deft) {
		addDefault(tableName, columnName, deft);
	}
	
	public void addIndex(TableName tableName, IndexName IndexName,ColumnName... columnName) {
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
		logger.info(sql);
	}

	public void addIndex(TableName tableName, IndexName IndexName, IndexType indexType,ColumnName... columnName) {
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
		logger.info(sql);
		
	}

	public void addPrimaryKey(String name, TableName tableName,ColumnName... columnName) {
		String sql=addKey(name, tableName, PRIMARY_KEY, columnName);
		logger.info(sql);
		
	}
	
	public void addForeignKey(String name, TableName tableName,ColumnName... columnName) {
		String sql=addKey(name, tableName, FOREIGN_KEY, columnName);
		logger.info(sql);
	}

	public void addUnique(String name, TableName tableName, ColumnName... columnName) {
		String sql=addKey(name, tableName, UNIQUE_KEY, columnName);
		logger.info(sql);
	}

	public void dropTable(String name) {
		String sql=generate.dropTalbe(name);
		logger.info(sql);
	}
	
	public void dropColumn(TableName tableName,ColumnName columnName) {
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String sql=generate.dropColumn(tn, cn);
		logger.info(sql);
		
	}

	public void dropIndex(String name) {
	    String sql=	generate.dropIndex(name);
		logger.info(sql);
	}
	
	public void dropForeignKey(TableName tableName,String name) {
		String sql=dropKey(tableName, name);
		logger.info(sql);
	}

	public void dropPrimaryKey(TableName tableName,String name) {
		String sql=dropKey(tableName, name);
		logger.info(sql);
		
	}

	

	public void dropUnique(TableName tableName,String name) {
		String sql=dropKey(tableName, name);
		logger.info(sql);
	}

	
	public void addComment(TableName tableName, ColumnName columnName,Comment comment) {
		TableNameImpl tableImpl=(TableNameImpl)tableName;
		ColumnNameImpl columnImpl=(ColumnNameImpl)columnName;
		CommentImpl commentImpl=(CommentImpl)comment;
		String tn=tableImpl.getName();
		String cn=columnImpl.getName();
		String c=commentImpl.getComment();
		String sql=generate.addComment(tn, cn, c);
		logger.info(sql);
	}
	
	
	public void updateComment(TableName tableName, ColumnName columnName,Comment comment) {
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
