/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Migration.java,fangj Exp$
 * created at:下午12:40:37
 */
package lichen.migration.services;


/**
 * 数据库迁移接口
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public interface Migration {
	
	public void up();
	
	public void down();

}
