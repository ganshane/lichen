// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.services;

import lichen.migration.model.ColumnOption;
import lichen.migration.model.IndexOption;
import lichen.migration.model.Name;
import lichen.migration.model.SqlType;
import lichen.migration.model.TableOption;

/**
 * 数据库升级脚本的帮助类
 * @author jcai
 */
public interface MigrationHelper {
    /**
     * 创建一张表
     * @param tableName 表名
     * @param body 创建表的回调函数
     * @param options 表的属性定义
     * @throws Throwable 任何错误，将抛出异常
     */
    void createTable(String tableName, TableCallback body, TableOption... options) throws Throwable;

    /**
     * 删除表
     * @param tableName 待删除的表名
     * @throws Throwable 任何错误，将抛出异常
     */
    void dropTable(String tableName) throws Throwable;

    /**
     * 向表中加入某一列
     * @param tableName 表名
     * @param columnName 列名
     * @param columnType 列的类型
     * @param options 列的定义
     * @throws Throwable 任何错误，将抛出异常
     */
    void addColumn(String tableName,
                   String columnName,
                   SqlType columnType,
                   ColumnOption... options) throws Throwable;

    /**
     * 删除列
     * @param tableName 表名
     * @param columnName 列名
     * @throws Throwable 任何错误，将抛出此异常
     */
    void removeColumn(String tableName,
                      String columnName) throws Throwable;

    /**
     * 修改一个存在的列
     *
     * @param tableName 表名
     * @param columnName 列名
     * @param columnType 列的类型
     * @param options 列的属性
     */
    void alterColumn(String tableName,
                     String columnName,
                     SqlType columnType,
                     ColumnOption... options) throws Throwable;

    
    /**
     * 针对某一列，创建数据库索引
     * @param tableName 表名
     * @param columnName 字段名称数据
     * @param options
     */
    void addIndex(String tableName,
            String columnName,
            IndexOption... options) throws Throwable; 
    
    
    /**
     * 针对多个列，创建数据库索引
     * @param tableName 表名
     * @param columnNames 字段名称数组
     * @param options
     */
    void addIndex(String tableName,
            String [] columnNames,
            IndexOption... options) throws Throwable;
    
    
    /**
     * 删除多个列创建的索引
     * @param tableName 表名
     * @param columnNames 字段名称数组
     * @param name
     */
    void removeIndex(String tableName,
            String [] columnNames,
            Name ... name);
    
}
