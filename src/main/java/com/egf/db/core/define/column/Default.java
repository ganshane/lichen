/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Default.java,fangj Exp$
 * created at:下午05:42:20
 */
package com.egf.db.core.define.column;

/**
 * 注释接口
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public interface Default extends ColumnDefine{
	/**
	 * 获取默认值
	 * @return
	 */
	public String getValue();
}
