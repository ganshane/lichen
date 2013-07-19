/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,IndexUniqueImpl.java,fangj Exp$
 * created at:下午01:53:02
 */
package lichen.migration.services.impl;

import lichen.migration.core.define.index.types.Unique;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
class IndexUniqueImpl implements Unique {

	public String getIndexType() {
		return "unique";
	}

}
