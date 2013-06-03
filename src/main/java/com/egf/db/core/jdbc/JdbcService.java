/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,JdbcManager.java,fangj Exp$
 * created at:上午09:59:39
 */
package com.egf.db.core.jdbc;

import java.sql.SQLException;
import java.util.List;

import com.egf.db.exception.MigrationException;

/**
 * jdbc service 接口
 * 
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public interface JdbcService {

	/**
	 * 自动提交执行sql语句
	 * @param sql sql语句
	 */
	public void execute(String sql) throws MigrationException;

	/**
	 * 自动提交执行sql语句
	 * @param sql sql语句
	 * @param params 参数
	 */
	public void execute(String sql, Object[] params) throws MigrationException;
	
	/**
	 * sql 查询,返回唯一结果
	 * @param sql
	 * @return
	 */
	public Object unique(String sql);
	
	/**
	 * sql 查询,返回唯一结果
	 * @param sql
	 * @param params
	 * @return
	 */
	public Object unique(String sql,Object[] params);
	
	
	/**
	 * sql查询
	 * @param sql sql语句
	 * @return
	 */
	public List<?> find(String sql);
	
	/**
	 * sql查询
	 * @param sql sql语句
	 * @param params 参数
	 * @return
	 */
	public List<?> find(String sql,Object[] params);
	
	/**
	 * 获取列的类型
	 * @param tableName  表名
	 * @param columnName 列名
	 * @return
	 */
	public String getColumnTypeName(String tableName,String columnName);

}
