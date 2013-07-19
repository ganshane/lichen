/*		
 * Copyright 2013-5-22 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,BlobImpl.java,fangj Exp$
 * created at:下午01:06:44
 */
package lichen.migration.services.impl;

import lichen.migration.core.define.column.types.Blob;

/**
 * blob类型接口实现
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
class BlobImpl implements Blob{

	public String getColumnType() {
		return "blob";
	}

}
