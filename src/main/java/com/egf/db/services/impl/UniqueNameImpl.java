/*		
 * Copyright 2013-6-3 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,UniqueNameImpl.java,fangj Exp$
 * created at:上午09:52:26
 */
package com.egf.db.services.impl;

import com.egf.db.core.define.name.UniqueName;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class UniqueNameImpl implements UniqueName {

	private String name;
	
	public String getName() {
		return name;
	}
	
	public UniqueNameImpl(String name){
		this.name=name;
	}

}
