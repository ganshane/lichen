/*		
 * Copyright 2013-6-3 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,ForeignKeyNameImpl.java,fangj Exp$
 * created at:上午09:55:51
 */
package lichen.migration.services.impl;

import lichen.migration.core.define.name.ForeignKeyName;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class ForeignKeyNameImpl implements ForeignKeyName {
	
	private String name;
	
	public ForeignKeyNameImpl(String name){
		this.name=name;
	}
	
	public String getName() {
		return this.name;
	}

}
