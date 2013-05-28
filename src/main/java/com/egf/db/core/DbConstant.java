/*		
 * Copyright 2013-5-24 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DbConstant.java,fangj Exp$
 * created at:上午11:19:36
 */
package com.egf.db.core;

/**
 * 数据常量定义
 * 
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class DbConstant {
	
	/**数据库jdbc文件名常量定义**/
	public final static String DEVELOPMENT_PROPERTIES = "development.properties";
	
	/** jdbc驱动常量定义 **/
	public final static String JDBC_DRIVER_CLASS = "jdbc.driverClass";
	/** jdbc连接url常量定义 **/
	public final static String JDBC_JDBCURL = "jdbc.jdbcUrl";
	/** jdbc连接用户常量定义 **/
	public final static String JDBC_USER = "jdbc.user";
	/** jdbc连接密码常量定义 **/
	public final static String JDBC_PASSWORD = "jdbc.password";
	/**版本控制表**/
	public final static String CHANGELOG="changelog";
	
	/**oracle驱动常量定义**/
	public final static String ORACLE_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	/**H2驱动常量定义**/
	public final static String H2_DRIVER_CLASS = "org.h2.Driver";
	/**mysql驱动常量定义**/
	public final static String MTSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	

}
