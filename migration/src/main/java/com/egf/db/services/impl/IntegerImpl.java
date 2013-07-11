/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,NumberImpl.java,fangj Exp$
 * created at:上午11:17:02
 */
package com.egf.db.services.impl;

import com.egf.db.core.define.column.types.Integer;
import com.egf.db.core.sql.template.GenerateFactory;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
class IntegerImpl implements Integer{

	public String getColumnType() {
		return GenerateFactory.getGenerate().getInteger();
	}

}
