/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DatabaseService.java,fangj Exp$
 * created at:下午12:42:36
 */
package com.egf.db.services;

import java.sql.SQLException;

import com.egf.db.core.CreateTableCallback;
import com.egf.db.core.define.ColumnType;
import com.egf.db.core.define.IndexType;
import com.egf.db.core.define.column.Comment;
import com.egf.db.core.define.column.Default;
import com.egf.db.core.define.column.NullOrNotNull;
import com.egf.db.core.define.name.ColumnName;
import com.egf.db.core.define.name.IndexName;
import com.egf.db.core.define.name.TableName;

/**
 * 数据库服务接口
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public interface DatabaseService {
	
	public void createTable(TableName tableName,Comment comment, CreateTableCallback callback) throws SQLException;
	
	public void createTable(TableName tableName, CreateTableCallback callback) throws SQLException;
	
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType) throws SQLException;
	
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType,Default deft) throws SQLException;
	
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType,NullOrNotNull nullOrNotNull) throws SQLException;
	
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType,Default deft,NullOrNotNull nullOrNotNull) throws SQLException;
	
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType,Comment comment)throws SQLException;
	
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType,Default deft,Comment comment) throws SQLException;
	
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType,NullOrNotNull nullOrNotNull,Comment comment)throws SQLException;
	
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType,Default deft,NullOrNotNull nullOrNotNull,Comment comment) throws SQLException;
	
	public void addComment(TableName tableName,ColumnName columnName,Comment comment) throws SQLException;
	
	public void addColumnNotNull(TableName tableName,ColumnName columnName) throws SQLException;
	
	public void addColumnNull(TableName tableName,ColumnName columnName) throws SQLException;
	
	public void updateComment(TableName tableName,ColumnName columnName,Comment comment) throws SQLException;
	
	public void addDefault(TableName tableName,ColumnName columnName,Default deft) throws SQLException;
	
	public void updateDefault(TableName tableName,ColumnName columnName,Default deft) throws SQLException;
	
	public void addIndex(TableName tableName,IndexName indexName, ColumnName ... columnName) throws SQLException;
	
	public void addIndex(TableName tableName,IndexName indexName,IndexType indexType, ColumnName ...columnName) throws SQLException;
	
	public void addPrimaryKey(String name,TableName tableName,ColumnName ... columnName) throws SQLException;
	
	public void addForeignKey(String name,TableName tableName,ColumnName ... columnName) throws SQLException;
	
	public void addUnique(String name,TableName tableName,ColumnName ... columnName) throws SQLException;
	
	public void dropTable(String name) throws SQLException;
	
	public void dropIndex(String name) throws SQLException;
	
	public void dropColumn(TableName tableName,ColumnName columnName) throws SQLException;
	
	public void dropPrimaryKey(TableName tableName,String name) throws SQLException;
	
	public void dropForeignKey(TableName tableName,String name) throws SQLException;
	
	public void dropUnique(TableName tableName,String name) throws SQLException;

	
}
