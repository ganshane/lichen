/*		
 * Copyright 2013-5-3 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Comment.java,fangj Exp$
 * created at:下午04:51:30
 */
package com.egf.db.core.define.column;


/**
 * 注释接口
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public interface Comment extends ColumnDefine{
	
	/**
	 * 获取注释
	 * @return
	 */
	public String getComment(); 
		
}
