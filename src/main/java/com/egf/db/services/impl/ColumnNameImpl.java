/*		
 * Copyright 2013-5-6 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,ColumnNameImpl.java,fangj Exp$
 * created at:上午11:40:01
 */
package com.egf.db.services.impl;

import com.egf.db.core.define.name.ColumnName;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
class ColumnNameImpl implements ColumnName {

	private String name;
	
	public String getName() {
		return name;
	}

	public ColumnNameImpl(String name){
		this.name=name;
	}
}
