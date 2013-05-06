/*		
 * Copyright 2013-5-6 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,TableImpl.java,fangj Exp$
 * created at:上午11:56:33
 */
package com.egf.db.services.impl;

import com.egf.db.core.define.name.TableName;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
class TableNameImpl implements TableName {

	private String name;
	
	public String getName() {
		return name;
	}

	public TableNameImpl(String name){
		this.name=name;
	}
}
