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
	
	/**数据库初始化**/
	public static boolean DB_VERSION_INIT=false;
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
	/**数据库脚本文件包路径常量定义**/
	public final static String DB_SCRIPT_PACKAGE = "db_script_package";
	/**版本控制表**/
	public final static String CHANGELOG="changelog";
	/**排序-升序**/
	public final static String SORT_ASC="asc";
	/**排序-降序**/
	public final static String SORT_DESC="desc";
	
	/**oracle驱动常量定义**/
	public final static String ORACLE_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	/**H2驱动常量定义**/
	public final static String H2_DRIVER_CLASS = "org.h2.Driver";
	/**mysql驱动常量定义**/
	public final static String MTSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	

}
