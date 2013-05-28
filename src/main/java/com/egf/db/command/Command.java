/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Commond.java,fangj Exp$
 * created at:下午01:44:07
 */
package com.egf.db.command;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public interface Command {

	/**
	 * 升级
	 * @param pack 存放脚本的包路径
	 */
	public void up(String pack);

	/**
	 * 回滚到指定版本
	 * 
	 * @param version
	 *            版本号
	 */
	public void down(String version);

}
