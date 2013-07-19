/*		
 * Copyright 2013-6-4 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,VarcharImpl.java,fangj Exp$
 * created at:下午02:25:16
 */
package lichen.migration.services.impl;

import lichen.migration.core.define.column.types.String;
import lichen.migration.core.sql.template.GenerateFactory;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
class StringImpl implements String {

	private int length;

	public StringImpl(int length) {
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	public java.lang.String getColumnType() {
		return GenerateFactory.getGenerate().getString(length);
	}

}
