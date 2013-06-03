/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Generate.java,fangj Exp$
 * created at:上午10:40:37
 */
package com.egf.db.core.sql.template;


/**
 * 生成sql语句类
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public interface Generate {

	/**
	 * 增加列
	 * @param tableName  表名
	 * @param columnName 列名
	 * @param columnType 类型
	 * @param columnDefine <br>
	 * 可选参数说明：<br>
	 * @param notNull 不为空<br>
	 * @param defaultValue 默认值<br>
	 * @param comment 注释<br>
	 * @param unique 唯一<br>
	 * @param primaryKey 主键
	 * @return
	 */
	public String addColumn(String tableName,String columnName,String columnType,String ...columnDefine);

	/**
	 * 修改列
	 * @param tableName  表名
	 * @param columnName 列名
	 * @param columnType 类型
	 * @param columnDefine <br>
	 * 可选参数说明：<br>
	 * @param notNull 不为空<br>
	 * @param defaultValue 默认值<br>
	 * @param comment 注释
	 * @return
	 */
	public String changeColumn(String tableName,String columnName,String columnType,String ...columnDefine);
	
	/**
	 * 增加约束
	 * @param tableName 表名
	 * @param name	约束名称
	 * @param type	类型
	 * 可选参数说明
	 * unique唯一
	 * primary key主键
	 * @param columnNames 
	 * 引用的列,可多选,例如：(cl1,cl2,cl3)
	 * @return
	 */
	public String addConstraint(String tableName,String name,String type,String ...columnNames);
	
	/**
	 * 增加外键
	 * @param tableName 表名
	 * @param name 外键名称
	 * @param referencesTable 引用的表
	 * @param referencesColumn 应用的表的列
	 * @param columnName 外键引用自身表的列
	 * 可多选,例如：(cl1,cl2,cl3)
	 * @return
	 */
	public String addForeignKey(String tableName, String name,String referencesTable, String referencesColumn, String... columnName);
	
	/**
	 * 增加索引
	 * @param tableName 表名
	 * @param indexName 索引名称
	 * @param columnName 
	 * 引用的列,可多选,例如：(cl1,cl2,cl3)
	 * @return
	 */
	public String addIndex(String tableName,String indexName,String... columnName);
	
	/**
	 * 增加索引
	 * @param tableName 表名
	 * @param indexName 索引名称
	 * @param type 索引类型
	 * 可选参数说明：
	 * normal
	 * unique
	 * bitmap
	 * @param columnName
	 * 引用的列,可多选,例如：(cl1,cl2,cl3)
	 * @return
	 */
	public String addIndex(String tableName,String indexName,String type,String... columnName);
	
	/**
	 * 删除表
	 * @param tableName 表名
	 * @return
	 */
	public String dropTalbe(String tableName);
	
	/**
	 * 删除列
	 * @param talbeName 表名
	 * @param columnName 列名
	 * @return
	 */
	public String dropColumn(String talbeName,String columnName);
	
	/**
	 * 删除索引
	 * @param indexName 索引名称
	 * @return
	 */
	public String dropIndex(String indexName);
	
	/**
	 * 删除约束
	 * @param tableName 表名
	 * @param name 约束名称
	 * @return
	 */
	public String dropConstraint(String tableName,String name);
	
	/**
	 * 重命名列
	 * @param tableName 表名
	 * @param oldColumnName 原列名
	 * @param newColumnName 新列名
	 * @return
	 */
	public String renameColumn(String tableName,String oldColumnName,String newColumnName);
}
