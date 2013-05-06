/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DBConnectionManager.java,fangj Exp$
 * created at:上午10:01:46
 */
package com.egf.db.core.jdbc;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.egf.db.core.config.SysConfigPropertyUtil;
import com.egf.db.utils.StringUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据库连接类
 * 
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class DBConnectionManager {

	private static final Logger logger = Logger.getLogger(DBConnectionManager.class);

	private static ComboPooledDataSource cpds;

	private static void init() {
		SysConfigPropertyUtil dbScpu = SysConfigPropertyUtil.getInstance("jdbc.properties");
		final String driver = dbScpu.getPropertyValue("jdbc.driverClass");
		final String url = dbScpu.getPropertyValue("jdbc.jdbcUrl");
		final String user = dbScpu.getPropertyValue("jdbc.user");
		final String password = dbScpu.getPropertyValue("jdbc.password");

		final int minPoolSize = StringUtils.isBlank(dbScpu.getPropertyValue("c3p0.minPoolSize")) ? 20 : Integer.parseInt(dbScpu.getPropertyValue("c3p0.minPoolSize"));
		final int maxPoolSize = StringUtils.isBlank(dbScpu.getPropertyValue("c3p0.maxPoolSize")) ? 100 : Integer.parseInt(dbScpu.getPropertyValue("c3p0.maxPoolSize"));
		final int initialPoolSize = StringUtils.isBlank(dbScpu.getPropertyValue("c3p0.initialPoolSize")) ? 100 : Integer.parseInt(dbScpu.getPropertyValue("c3p0.initialPoolSize"));
		final int acquireIncrement = StringUtils.isBlank(dbScpu.getPropertyValue("c3p0.acquireIncrement")) ? 5 : Integer.parseInt(dbScpu.getPropertyValue("c3p0.acquireIncrement"));
		final int maxIdleTime = StringUtils.isBlank(dbScpu.getPropertyValue("c3p0.maxIdleTime")) ? 60 : Integer.parseInt(dbScpu.getPropertyValue("c3p0.maxIdleTime"));
		final boolean connectionValidate = StringUtils.isBlank(dbScpu.getPropertyValue("c3p0.connectionValidate")) ? false : Boolean.getBoolean(dbScpu.getPropertyValue("c3p0.maxIdleTime"));

		try {
			cpds=new ComboPooledDataSource();
			cpds.setDriverClass(driver);
		} catch (PropertyVetoException e) {
			logger.error("数据库初始化连接出错:" + e.getMessage());
			e.printStackTrace();
		}
		cpds.setJdbcUrl(url);
		cpds.setUser(user);
		cpds.setPassword(password);
		cpds.setInitialPoolSize(initialPoolSize);
		cpds.setMinPoolSize(minPoolSize);
		cpds.setMaxPoolSize(maxPoolSize);
		cpds.setAcquireIncrement(acquireIncrement);
		cpds.setIdleConnectionTestPeriod(maxIdleTime);
		cpds.setTestConnectionOnCheckout(connectionValidate);
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			if (cpds == null) {
				init();
			}
			connection = cpds.getConnection();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return connection;
	}

	public static void release() {
		try {
			if (cpds != null) {
				cpds.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
