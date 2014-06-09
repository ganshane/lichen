// Copyright 2013 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package lichen.migration.services;

import lichen.migration.model.*;


/**
 * 数据库升级脚本的帮助类.
 *
 * @author jcai
 */
public interface MigrationHelper {
    /**
     * 创建一张表.
     *
     * @param tableName 表名
     * @param body      创建表的回调函数
     * @param options   表的属性定义
     * @throws Throwable 任何错误，将抛出异常
     */
    void createTable(String tableName, TableCallback body, TableOption... options) throws Throwable;

    /**
     * 删除表.
     *
     * @param tableName 待删除的表名
     * @throws Throwable 任何错误，将抛出异常
     */
    void dropTable(String tableName) throws Throwable;

    /**
     * 向表中加入某一列.
     *
     * @param tableName  表名
     * @param columnName 列名
     * @param columnType 列的类型
     * @param options    列的定义
     * @throws Throwable 任何错误，将抛出异常
     */
    void addColumn(String tableName,
                   String columnName,
                   SqlType columnType,
                   ColumnOption... options) throws Throwable;

    /**
     * 删除列.
     *
     * @param tableName  表名
     * @param columnName 列名
     * @throws Throwable 任何错误，将抛出此异常
     */
    void removeColumn(String tableName,
                      String columnName) throws Throwable;

    /**
     * 修改一个存在的列.
     *
     * @param tableName  表名
     * @param columnName 列名
     * @param columnType 列的类型
     * @param options    列的属性
     */
    void alterColumn(String tableName,
                     String columnName,
                     SqlType columnType,
                     ColumnOption... options) throws Throwable;

    /**
     * 增加列注释
     * @param tableName 表名
     * @param columnName 列名
     * @param comment 注释名称
     * @throws Throwable
     */
    void commentColumn(String tableName,String columnName,Comment comment) throws Throwable;
    
    /**
     * 增加表注释
     * @param tableName 表名
     * @param comment 注释
     * @throws Throwable
     */
    void commentTable(String tableName,Comment comment) throws Throwable;

    /**
     * 针对某一列，创建数据库索引.
     *
     * @param tableName  表名
     * @param columnName 字段名称数据
     * @param options
     */
    void addIndex(String tableName, String columnName, IndexOption... options) throws Throwable;

    /**
     * 针对多个列，创建数据库索引.
     *
     * @param tableName   表名
     * @param columnNames 字段名称数组
     * @param options
     */
    void addIndex(String tableName,
                  String[] columnNames,
                  IndexOption... options) throws Throwable;

    /**
     * 删除单个列创建的索引.
     *
     * @param tableName   表名
     * @param columnNames 字段名称数组
     * @param name
     */
    void removeIndex(String tableName,
                     String columnName,
                     Name... name) throws Throwable;

    /**
     * 删除多个列创建的索引.
     *
     * @param tableName   表名
     * @param columnNames 字段名称数组
     * @param name
     */
    void removeIndex(String tableName,
                     String[] columnNames,
                     Name... name) throws Throwable;
    
    /**
     * 执行sql语句
     * @param sql
     * @throws Throwable
     */
    void executeSQL(String ... sql) throws Throwable;
    
    /**
     * 删除序列
     * @param sequenceName 待删除的序列名
     * @throws Throwable   任何错误，将抛出异常
     */
    void dropSequence(String sequenceName) throws Throwable;
    
    /**
     * 创建序列
     * @param seq_name 序列名
     * @param options 序列属性
     * @throws Throwable 任何错误，将抛出异常
     */
    void createSequence(String seq_name, SequenceOption... options) throws Throwable;

}
