/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,IndexNameImpl.java,fangj Exp$
 * created at:上午10:00:33
 */
package lichen.migration.services.impl;

import lichen.migration.core.define.name.IndexName;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
class IndexNameImpl implements IndexName {

	private String name;

	public IndexNameImpl(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
