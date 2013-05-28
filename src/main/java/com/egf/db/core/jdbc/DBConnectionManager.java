/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DBConnectionManager.java,fangj Exp$
 * created at:上午10:01:46
 */
package com.egf.db.core.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

import com.egf.db.core.DbConstant;
import com.egf.db.core.config.SysConfigPropertyUtil;

/**
 * 数据库连接类
 * 
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class DBConnectionManager {

	private static final Logger logger = Logger.getLogger(DBConnectionManager.class);

	private static String driver;
	private static String url;
	private static String dbUser;
	private static String dbPassword;

	public static void getParam() {
		SysConfigPropertyUtil dbScpu = SysConfigPropertyUtil.getInstance(DbConstant.DEVELOPMENT_PROPERTIES);
		driver = dbScpu.getPropertyValue(DbConstant.JDBC_DRIVER_CLASS);
		url = dbScpu.getPropertyValue(DbConstant.JDBC_JDBCURL);
		dbUser = dbScpu.getPropertyValue(DbConstant.JDBC_USER);
		dbPassword = dbScpu.getPropertyValue(DbConstant.JDBC_PASSWORD);
	}

	public static Connection getConnection() {
		getParam();
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, dbUser, dbPassword);
		} catch (Exception e) {
			logger.error("数据连接异常:" + e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}

}
