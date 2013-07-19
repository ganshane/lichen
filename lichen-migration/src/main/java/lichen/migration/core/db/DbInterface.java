/*		
 * Copyright 2013-5-24 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DbInterface.java,fangj Exp$
 * created at:上午10:54:44
 */
package lichen.migration.core.db;

import java.sql.SQLException;

import lichen.migration.exception.MigrationException;

/**
 * 数据库接口
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public interface DbInterface {
	
	/**
	 * 获取主键列
	 * @param tableName 表名
	 * @return
	 */
	public String[] getPrimaryKeyColumn(String tableName);
	
	/**
	 * 获取主键名称
	 * @param tableName
	 * @return
	 */
	public String getPrimaryKeyName(String tableName);
	
	/**
	 * 创建用户
	 * @param schema 用户名称
	 * @throws SQLException
	 */
	public void createSchema(String schema) throws MigrationException;
	
	/**
	 * 是否存在表
	 * @param tableName 表名
	 * @return
	 */
	public boolean existsTable(String tableName);
	
	
	/**
	 * 获取列的类型
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public String getColumnType(String tableName,String columnName);
	
	/**
	 * 列是否为空
	 * @param tableName 表名
	 * @param columnName 列名
	 * @return
	 */
	public boolean columnIsNotNull(String tableName,String columnName);
	
}
