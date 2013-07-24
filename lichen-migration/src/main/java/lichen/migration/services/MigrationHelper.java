// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.services;

import lichen.migration.model.ColumnOption;
import lichen.migration.model.SqlType;
import lichen.migration.model.TableDefinition;
import lichen.migration.model.TableOption;
import lichen.migration.services.TableCallback;

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


}
