/*		
 * Copyright 2013-5-29 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DateImpl.java,fangj Exp$
 * created at:上午09:29:54
 */
package lichen.migration.services.impl;

import lichen.migration.core.define.column.types.Date;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
class DateImpl implements Date{

	public String getColumnType() {
		return "date";
	}

}
