/*		
 * Copyright 2013-6-4 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,GenerateFactory.java,fangj Exp$
 * created at:下午03:32:48
 */
package lichen.migration.core.sql.template;

import lichen.migration.core.DbConstant;
import lichen.migration.core.config.SysConfigPropertyUtil;

/**
 * @author fangj
 * @version $Revision: 2.0.2 $
 * @since 1.0
 */
public class GenerateFactory {
	
	private static SysConfigPropertyUtil scpu = SysConfigPropertyUtil.getInstance();

	private GenerateFactory() {
	}

	public static Generate getGenerate() {
		String driverClass = scpu.getPropertyValue(DbConstant.JDBC_DRIVER_CLASS);
		if (driverClass == null) {
			return new H2Generate();
		} else {
			if (DbConstant.ORACLE_DRIVER_CLASS.equals(driverClass)) {
				return new OracleGenerate();
			} else if (DbConstant.H2_DRIVER_CLASS.equals(driverClass)) {
				return new H2Generate();
			} else if (DbConstant.MYSQL_DRIVER_CLASS.equals(driverClass)) {
				return new MysqlGenerate();
			} else if(DbConstant.DB2_DRIVER_CLASS.equals(driverClass)){
				return new Db2Generate();
			}else {
				// 默认H2数据库
				return new H2Generate();
			}
		}
	}
}