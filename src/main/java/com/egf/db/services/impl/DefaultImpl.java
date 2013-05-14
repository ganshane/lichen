/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DefaultImpl.java,fangj Exp$
 * created at:下午05:42:51
 */
package com.egf.db.services.impl;

import com.egf.db.core.define.column.Default;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class DefaultImpl implements Default {
	private String value;

	public String getValue() {
		return value;
	}

	public DefaultImpl(String value){
		this.value=value;
	}
}
