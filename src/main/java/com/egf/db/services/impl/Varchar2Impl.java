/*		
 * Copyright 2013-5-6 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Varchar2Impl.java,fangj Exp$
 * created at:上午11:49:12
 */
package com.egf.db.services.impl;

import com.egf.db.core.define.column.types.Varchar2;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class Varchar2Impl implements Varchar2 {
	private int length;
	
	public Varchar2Impl(int length){
		this.length=length;
	}

	public int getLength() {
		return length;
	}
	
	public String getColumnType() {
		return "varchar2("+length+")";
	}

}
