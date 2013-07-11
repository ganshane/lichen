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
import com.egf.db.core.define.column.ColumnDefine;
import com.egf.db.core.define.column.Comment;
import com.egf.db.core.define.name.ColumnName;
import com.egf.db.core.define.name.ForeignKeyName;
import com.egf.db.core.define.name.IndexName;
import com.egf.db.core.define.name.PrimaryKeyName;
import com.egf.db.core.define.name.SequenceName;
import com.egf.db.core.define.name.TableName;
import com.egf.db.core.define.name.UniqueName;
import com.egf.db.exception.MigrationException;

/**
 * 数据库服务接口
 * @author fangj
 * @version $Revision: 2.0.1 $
 * @since 1.0
 */
public interface DatabaseService {
	
	/**
	 * 创建表
	 * @param tableName 表名
	 * @param comment 表注释
	 * @param createTableCallback 回调函数
	 * @throws SQLException
	 */
	public void createTable(TableName tableName,Comment comment, CreateTableCallback createTableCallback) throws MigrationException;
	
	/**
	 * 创建表
	 * @param tableName 表名
	 * @param createTableCallback 表注释
	 * @throws SQLException
	 */
	public void createTable(TableName tableName, CreateTableCallback createTableCallback) throws MigrationException;
	
	/**
	 * 创建序列
	 * @param sequenceName
	 */
	public void createSequence(SequenceName sequenceName);
	
	
	/**
	 * 添加列
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param columnType 列类型
	 * @param define 列定义<br/>
	 * 可选参数：
	 * Default 默认值<br/>
	 * Null,NotNull 是否可为空<br/>
	 * Comment 注释
	 * @throws MigrationException
	 */
	public void addColumn(TableName tableName,ColumnName columnName,ColumnType columnType,ColumnDefine  ... define) throws MigrationException;
	
	
	/**
	 * 改变列
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param columnType 列类型
	 * @param define 列定义<br/>
	 * 可选参数：
	 * Default 默认值<br/>
	 * Null,NotNull 是否可为空<br/> 
	 * Comment 注释
	 * @throws MigrationException
	 */
	public void changeColumn(TableName tableName,ColumnName columnName,ColumnType columnType,ColumnDefine  ... define) throws MigrationException;
	

	/**
	 * 改变列
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param define 列定义<br/>
	 * 可选参数：
	 * Default 默认值<br/>
	 * Null,NotNull 是否可为空<br/> 
	 * Comment 注释
	 * @throws MigrationException
	 */
	public void changeColumn(TableName tableName,ColumnName columnName,ColumnDefine  ... define) throws MigrationException;
		
	
	/**
	 * 从命名列
	 * @param tableName 表名
	 * @param oldColumnName 原列名
	 * @param newColumnName 新列名
	 */
	public void renameColumn(TableName tableName,ColumnName oldColumnName,ColumnName newColumnName);
	
	
	/**
	 * 添加索引
	 * @param tableName 表名
	 * @param indexName 索引名称
	 * @param columnName 列名
	 * @throws MigrationException
	 */
	public void addIndex(TableName tableName,IndexName indexName, ColumnName ... columnName) throws MigrationException;
	
	
	/**
	 * 添加索引
	 * @param tableName 表名
	 * @param columnName 列名
	 * @throws MigrationException
	 */
	public void addIndex(TableName tableName,ColumnName ... columnName) throws MigrationException;

	
	/**
	 * 添加主键
	 * @param primaryKeyName 主键名称
	 * @param tableName 表名
	 * @param columnName 列名(可多选,譬如：columnName1,columnName2)
	 * @throws MigrationException
	 */
	public void addPrimaryKey(PrimaryKeyName primaryKeyName,TableName tableName,ColumnName ... columnName) throws MigrationException;
	
	/**
	 * 添加外键
	 * @param primaryKeyName 名称
	 * @param tableName 表名
	 * @param refTableName 引用表
	 * @param columnName 设置外键的列(可多选,譬如：columnName1,columnName2)
	 * @throws MigrationException
	 */
	public void addForeignKey(ForeignKeyName primaryKeyName,TableName tableName,TableName refTableName,ColumnName ... columnName) throws MigrationException;
	
	/**
	 * 添加唯一约束
	 * @param name 约束名称
	 * @param tableName 表名
	 * @param columnName 列名
	 * @throws MigrationException
	 */
	public void addUnique(UniqueName uniqueName,TableName tableName,ColumnName ... columnName) throws MigrationException;
	
	/**
	 * 删除表
	 * @param name 表名
	 * @throws MigrationException
	 */
	public void dropTable(TableName tableName) throws MigrationException;
	
	/**
	 * 删除序列
	 * @param sequenceName 序列名称
	 */
	public void dropSequence(SequenceName sequenceName);
	
	/**
	 * 删除索引
	 * @param name 索引名称
	 * @throws MigrationException
	 */
	public void dropIndex(IndexName indexName) throws MigrationException;
	
	/**
	 * 删除列
	 * @param tableName 表名
	 * @param columnName 列名
	 * @throws MigrationException
	 */
	public void dropColumn(TableName tableName,ColumnName columnName) throws MigrationException;
	
	/**
	 * 删除主键约束
	 * @param tableName 表名
	 * @throws MigrationException
	 */
	public void dropPrimaryKey(TableName tableName) throws MigrationException;
	
	/**
	 * 删除外键约束
	 * @param tableName 表名
	 * @param name 约束名称
	 * @throws MigrationException
	 */
	public void dropForeignKey(TableName tableName,ForeignKeyName foreignKeyName) throws MigrationException;
	
	/**
	 * 删除唯一约束
	 * @param tableName 表名
	 * @param name 唯一约束名称
	 * @throws MigrationException
	 */
	public void dropUnique(TableName tableName,UniqueName uniqueName) throws MigrationException;

}
