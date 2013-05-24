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
import com.egf.db.core.define.column.ColumnDefine;
import com.egf.db.core.define.column.Comment;
import com.egf.db.core.define.column.Default;
import com.egf.db.core.define.column.NotNull;
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
	
	/**
	 * 创建表
	 * @param tableName 表名
	 * @param comment 表注释
	 * @param callback 回调函数
	 * @throws SQLException
	 */
	public void createTable(TableName tableName,Comment comment, CreateTableCallback callback) throws SQLException;
	
	/**
	 * 创建表
	 * @param tableName 表名
	 * @param callback 表注释
	 * @throws SQLException
	 */
	public void createTable(TableName tableName, CreateTableCallback callback) throws SQLException;
	
	/**
	 * 添加列
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param columnType 列类型
	 * @throws SQLException 
	 */
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType) throws SQLException;
	
	/**
	 * 添加列
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param columnType 列类型
	 * @param define 列定义
	 * 可选参数：
	 * Default 默认值
	 * Null,NotNull 是否可为空 
	 * Comment 注释
	 * @throws SQLException
	 */
	public void addColumn(TableName tableName,ColumnName columnName,ColumnType columnType,ColumnDefine  ... define) throws SQLException;
	
	/**
	 *  添加列
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param columnType  列类型
	 * @param deft	默认值
	 * @throws SQLException
	 */
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType,Default deft) throws SQLException;
	
	/**
	 * 添加列
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param columnType 列类型
	 * @param nullOrNotNull 是否可为空
	 * @throws SQLException
	 */
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType,NotNull notNull) throws SQLException;
	
	/**
	 *  添加列
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param columnType 列类型
	 * @param deft 默认值
	 * @param nullOrNotNull 是否可为空
	 * @throws SQLException
	 */
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType,Default deft,NotNull notNull) throws SQLException;
	
	/**
	 *  添加列
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param columnType 列类型
	 * @param comment 注释
	 * @throws SQLException
	 */
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType,Comment comment)throws SQLException;
	
	/**
	 * 添加列
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param columnType 列类型
	 * @param deft 默认值
	 * @param comment 注释
	 * @throws SQLException
	 */
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType,Default deft,Comment comment) throws SQLException;
	
	/**
	 * 添加列
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param columnType 列类型
	 * @param nullOrNotNull 是否可为空
	 * @param comment 注释
	 * @throws SQLException
	 */
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType,NotNull notNull,Comment comment)throws SQLException;
	
	/**
	 * 添加列
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param columnType 列类型
	 * @param deft 默认值
	 * @param nullOrNotNull 是否可为空
	 * @param comment 注释
	 * @throws SQLException
	 */
	public void addColumn(TableName tableName,ColumnName columnName ,ColumnType columnType,Default deft,NotNull notNull,Comment comment) throws SQLException;
	
	/**
	 * 添加列注释
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param comment 注释
	 * @throws SQLException
	 */
	public void addComment(TableName tableName,ColumnName columnName,Comment comment) throws SQLException;
	
	/**
	 * 添加列不为空
	 * @param tableName 表名
	 * @param columnName 列名
	 * @throws SQLException
	 */
	public void addColumnNotNull(TableName tableName,ColumnName columnName) throws SQLException;
	
	/**
	 * 添加列为空
	 * @param tableName 表名
	 * @param columnName 列名
	 * @throws SQLException
	 */
	public void addColumnNull(TableName tableName,ColumnName columnName) throws SQLException;
	
	/**
	 * 修改列注释
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param comment 注释
	 * @throws SQLException
	 */
	public void updateComment(TableName tableName,ColumnName columnName,Comment comment) throws SQLException;
	
	/**
	 * 添加默认值
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param deft 默认值
	 * @throws SQLException
	 */
	public void addDefault(TableName tableName,ColumnName columnName,Default deft) throws SQLException;
	
	/**
	 * 修改默认值
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param deft 默认值
	 * @throws SQLException
	 */
	public void updateDefault(TableName tableName,ColumnName columnName,Default deft) throws SQLException;
	
	/**
	 * 添加索引
	 * @param tableName 表名
	 * @param indexName 索引名称
	 * @param columnName 列名
	 * @throws SQLException
	 */
	public void addIndex(TableName tableName,IndexName indexName, ColumnName ... columnName) throws SQLException;
	
	/**
	 * 添加索引
	 * @param tableName 表名
	 * @param indexName 索引名称
	 * @param indexType 索引类型
	 * @param columnName 列名(可多选,譬如:columnName1,columnName2)
	 * @throws SQLException
	 */
	public void addIndex(TableName tableName,IndexName indexName,IndexType indexType, ColumnName ...columnName) throws SQLException;
	
	/**
	 * 添加主键
	 * @param name 主键名称
	 * @param tableName 表名
	 * @param columnName 列名(可多选,譬如：columnName1,columnName2)
	 * @throws SQLException
	 */
	public void addPrimaryKey(String name,TableName tableName,ColumnName ... columnName) throws SQLException;
	
	/**
	 * 添加外键
	 * @param name 名称
	 * @param tableName 表名
	 * @param refTableName 引用表
	 * @param columnName 设置外键的列(可多选,譬如：columnName1,columnName2)
	 * @throws SQLException
	 */
	public void addForeignKey(String name,TableName tableName,TableName refTableName,ColumnName ... columnName) throws SQLException;
	
	/**
	 * 添加唯一约束
	 * @param name 约束名称
	 * @param tableName 表名
	 * @param columnName 列名
	 * @throws SQLException
	 */
	public void addUnique(String name,TableName tableName,ColumnName ... columnName) throws SQLException;
	
	/**
	 * 删除表
	 * @param name 表名
	 * @throws SQLException
	 */
	public void dropTable(String name) throws SQLException;
	
	/**
	 * 删除索引
	 * @param name 索引名称
	 * @throws SQLException
	 */
	public void dropIndex(String name) throws SQLException;
	
	/**
	 * 删除列
	 * @param tableName 表名
	 * @param columnName 列名
	 * @throws SQLException
	 */
	public void dropColumn(TableName tableName,ColumnName columnName) throws SQLException;
	
	/**
	 * 删除主键约束
	 * @param tableName 表名
	 * @throws SQLException
	 */
	public void dropPrimaryKey(TableName tableName) throws SQLException;
	
	/**
	 * 删除外键约束
	 * @param tableName 表名
	 * @param name 约束名称
	 * @throws SQLException
	 */
	public void dropForeignKey(TableName tableName,String name) throws SQLException;
	
	/**
	 * 删除唯一约束
	 * @param tableName 表名
	 * @param name 唯一约束名称
	 * @throws SQLException
	 */
	public void dropUnique(TableName tableName,String name) throws SQLException;

}
