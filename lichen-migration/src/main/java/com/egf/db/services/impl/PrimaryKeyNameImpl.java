/*		
 * Copyright 2013-5-31 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,PrimaryKeyNameImpl.java,fangj Exp$
 * created at:下午05:49:29
 */
package com.egf.db.services.impl;

import com.egf.db.core.define.name.PrimaryKeyName;

/**
 * 主键名称接口实现类
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class PrimaryKeyNameImpl implements PrimaryKeyName {

	private String name;

	public PrimaryKeyNameImpl(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
