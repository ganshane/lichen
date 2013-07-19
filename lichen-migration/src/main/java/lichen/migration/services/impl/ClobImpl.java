/*		
 * Copyright 2013-5-29 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,ClobImpl.java,fangj Exp$
 * created at:上午09:26:54
 */
package lichen.migration.services.impl;

import lichen.migration.core.define.column.types.Clob;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
class ClobImpl implements Clob {

	public String getColumnType() {
		return "clob";
	}

}
