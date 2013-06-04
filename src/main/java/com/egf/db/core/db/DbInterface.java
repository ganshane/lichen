/*		
 * Copyright 2013-5-24 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DbInterface.java,fangj Exp$
 * created at:上午10:54:44
 */
package com.egf.db.core.db;

import java.sql.SQLException;

import com.egf.db.exception.MigrationException;

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
	 * 重命名列sql
	 * @param tableName
	 * @param oldColumnName
	 * @param newColumnName
	 */
	public String renameColumnName(String tableName,String oldColumnName,String newColumnName);
	
}
