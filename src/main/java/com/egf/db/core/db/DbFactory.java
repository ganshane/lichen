/*		
 * Copyright 2013-5-24 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DbFactoryImpl.java,fangj Exp$
 * created at:上午11:15:16
 */
package com.egf.db.core.db;

import com.egf.db.core.DbConstant;
import com.egf.db.core.config.SysConfigPropertyUtil;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class DbFactory {

	private static SysConfigPropertyUtil scpu = SysConfigPropertyUtil.getInstance();

	private DbFactory() {}

	public static DbInterface getDb() {
		String driverClass = scpu.getPropertyValue(DbConstant.JDBC_DRIVER_CLASS);
		if (driverClass == null) {
			return new DbH2Impl();
		} else {
			if (DbConstant.ORACLE_DRIVER_CLASS.equals(driverClass)) {
				return new DbOracleImpl();
			} else if (DbConstant.H2_DRIVER_CLASS.equals(driverClass)) {
				return new DbH2Impl();
			} else if (DbConstant.MYSQL_DRIVER_CLASS.equals(driverClass)) {
				return new DbMysqlImpl();
			} else if(DbConstant.DB2_DRIVER_CLASS.equals(driverClass)){
				return new DbDb2Impl();
			}else {
				// 默认H2数据库
				return new DbH2Impl();
			}
		}
	}

}
