/*		
 * Copyright 2013-5-3 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,ClientCommond.java,fangj Exp$
 * created at:下午05:09:26
 */
package com.egf.db.command.support;

import java.sql.SQLException;
import java.util.Set;

import org.apache.log4j.Logger;

import com.egf.db.command.Command;
import com.egf.db.core.DbConstant;
import com.egf.db.core.config.SysConfigPropertyUtil;
import com.egf.db.core.db.DbFactory;
import com.egf.db.core.db.DbInterface;
import com.egf.db.core.jdbc.JdbcService;
import com.egf.db.core.jdbc.JdbcServiceImpl;
import com.egf.db.services.impl.AbstractMigration;
import com.egf.db.utils.DateTimeUtils;
import com.egf.db.utils.StringUtils;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class ClientCommand implements Command {

	Logger logger = Logger.getLogger(ClientCommand.class);
	String pack = SysConfigPropertyUtil.getInstance().getPropertyValue(DbConstant.DB_SCRIPT_PACKAGE);
	AbstractMigration am = null;
	String timeId = null;
	String handle = null;
	boolean flag = true;
	
	public void up() {
		// 查询数据库当前版本
		String newDbVersion = getNewDbVersion();
		Set<Class<?>> classSet = DbScriptFileClassFind.getDbScriptClasses(pack,DbConstant.SORT_ASC);
		// 安文件时间先后排序
		for (Class<?> cls : classSet) {
			String className = cls.getName();
			String fileName = className.substring(className.lastIndexOf(".") + 1, className.length());
			handle = fileName.split("_")[0];
			timeId = fileName.split("_")[1];
			if (StringUtils.isBlank(newDbVersion)|| timeId.compareTo(newDbVersion) > 0) {
				logger.info("\n" + cls.getName() + " up script start run...");
				try {
					am = (AbstractMigration) cls.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					am.up();
				} catch (SQLException e) {
					flag = false;
					break;
				}
				finally {
					// 升级操作
					saveLog(timeId, handle);
				}
			}
		}
	}

	public void down(String version) {
		// 查询数据库当前版本
		String newDbVersion = getNewDbVersion();
		Set<Class<?>> classSet = DbScriptFileClassFind.getDbScriptClasses(pack,DbConstant.SORT_DESC);
		for (Class<?> cls : classSet) {
			String className = cls.getName();
			String fileName = className.substring(className.lastIndexOf(".") + 1, className.length());
			handle = fileName.split("_")[0];
			timeId = fileName.split("_")[1];
			if (timeId.compareTo(version) >= 0&& timeId.compareTo(newDbVersion) <= 0) {
				logger.info("\n" + cls.getName() + " down script start run...");
				try {
					am = (AbstractMigration) cls.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					am.down();
				} catch (SQLException e) {
					flag = false;
					break;
				}
				if (flag) {
					// 回滚操作
					deleteLog(timeId);
				}
			}
		}
	}

	/**
	 * 保存版本信息
	 * 
	 * @param timeId
	 *            id
	 * @param handle
	 *            操作描述
	 */
	private void saveLog(String timeId, String handle) {
		JdbcService js = new JdbcServiceImpl();
		String database_changelog = SysConfigPropertyUtil.getInstance().getPropertyValue(DbConstant.CHANGELOG);
		String sql = String.format("insert into %s (id,applied_at,description) values('%s','%s','%s')",database_changelog, timeId, DateTimeUtils.getNowTimeNormalString(), handle);
		try {
			logger.info("\n记录版本信息:" + sql);
			js.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("\n记录版本信息出错:" + e.getMessage());
		}
	}
	
	/**
	 * 删除日志信息
	 * @param id
	 */
	private void deleteLog(String id){
		String database_changelog = SysConfigPropertyUtil.getInstance().getPropertyValue(DbConstant.CHANGELOG);
		String sql=String.format("delete from %s where id='%s'",database_changelog,id);
		JdbcService js = new JdbcServiceImpl();
		try {
			logger.info("\n回滚版本信息:" + sql);
			js.execute(sql);
		} catch (SQLException e) {
			logger.error("删除数据库升级日志出错:"+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库最新版本
	 * 
	 * @return
	 */
	private String getNewDbVersion() {
		JdbcService js = new JdbcServiceImpl();
		String database_changelog = SysConfigPropertyUtil.getInstance().getPropertyValue(DbConstant.CHANGELOG);
		String sql = String.format("select max(id) from %s", database_changelog);
		String newDbVersion = (String) js.unique(sql);
		return newDbVersion;
	}
	
	public void init(){
		JdbcService js = new JdbcServiceImpl();
		String database_changelog = SysConfigPropertyUtil.getInstance().getPropertyValue(DbConstant.CHANGELOG);
		if(database_changelog.indexOf(".")!=-1){
			//创建用户
			DbInterface di=DbFactory.getDb();
			String schema=database_changelog.split("\\.")[0];
			try {
				di.createSchema(schema);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String sql=String.format("create table %s (\nid varchar2(20) primary key,\napplied_at varchar2(25),description varchar2(255)\n);",database_changelog);
		try {
			js.execute(sql);
		} catch (SQLException e) {
			logger.error("初始化数据失败:"+e.getMessage());
			e.printStackTrace();
		}
	}
}
