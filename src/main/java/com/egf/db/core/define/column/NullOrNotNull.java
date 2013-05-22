/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,NullOrNotNull.java,fangj Exp$
 * created at:下午03:14:48
 */
package com.egf.db.core.define.column;

/**
 * 为空不为空接口
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public interface NullOrNotNull extends ColumnDefine{
	/**
	 * 输出结果
	 * @return
	 */
	public String out();
}
