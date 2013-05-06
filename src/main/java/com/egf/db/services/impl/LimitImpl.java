/*		
 * Copyright 2013-5-6 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,LimitImpl.java,fangj Exp$
 * created at:上午11:45:17
 */
package com.egf.db.services.impl;

import com.egf.db.core.define.column.Limit;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
class LimitImpl implements Limit {

	private int length;
	
	public int getLimit() {
		return length;
	}

	public LimitImpl(int length){
		this.length=length;
	}
}
