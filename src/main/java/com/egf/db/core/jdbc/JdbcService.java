/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,JdbcManager.java,fangj Exp$
 * created at:上午09:59:39
 */
package com.egf.db.core.jdbc;

import java.sql.SQLException;
import java.util.List;

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
	 * 
	 * @param sql
	 *            sql语句
	 */
	public void execute(String sql) throws SQLException;

	/**
	 * 自动提交执行sql语句
	 * 
	 * @param sql
	 *            ql语句
	 * @param params
	 *            参数
	 */
	public void execute(String sql, Object[] params) throws SQLException;
	
	public List<Object[]> find(String sql);
	
	
	public List<Object[]> find(String sql,Object[] params);
	
	
	public String getColumnTypeName(String tableName,String columnName);

}
