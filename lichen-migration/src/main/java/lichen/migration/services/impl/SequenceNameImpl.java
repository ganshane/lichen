/*		
 * Copyright 2013-6-14 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,SequenceNameImpl.java,fangj Exp$
 * created at:上午09:12:58
 */
package lichen.migration.services.impl;

import lichen.migration.core.define.name.SequenceName;

/**
 * 序列实现类
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
class SequenceNameImpl implements SequenceName {

	private String name;

	public SequenceNameImpl(String name){
		this.name=name;
	}
	
	public String getName() {
		return this.name;
	}

}
