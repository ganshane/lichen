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

	public void up(String pack) {
		AbstractMigration am = null;
		String timeId=null;
		String handle=null;
		Set<Class<?>> classSet = DbScriptFileClassFind.getDbScriptClasses(pack);
		//安文件时间先后排序
		for (Class<?> cls : classSet) {
			String className=cls.getName();
			String fileName=className.substring(className.lastIndexOf(".")+1,className.length());
			handle=fileName.split("_")[0];
			timeId=fileName.split("_")[1];
			logger.info("\n"+cls.getName() + " up script start run...");
			try {
				am = (AbstractMigration) cls.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			try {
				am.up();
			} catch (SQLException e) {
				saveLog(timeId, handle);
				break;
			}
		}
		if(!StringUtils.isBlank(timeId)&&!StringUtils.isBlank(handle)){
			saveLog(timeId, handle);
		}
	}

	public void down(String version) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 保存版本信息
	 * @param timeId id
	 * @param handle 操作描述
	 */
	private void saveLog(String timeId,String handle){
		JdbcService js=new JdbcServiceImpl();
		String database_changelog=SysConfigPropertyUtil.getInstance().getPropertyValue(DbConstant.CHANGELOG);
		String sql =String.format("insert into "+database_changelog+"(id,applied_at,description) values('%s','%s','%s')",timeId,DateTimeUtils.getNowTimeNormalString(),handle);
		try {
			logger.info("\n记录版本信息:"+sql);
			js.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("\n记录版本信息出错:"+e.getMessage());
		}
	}
}
