/*		
 * Copyright 2013-5-3 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Varchar2Define.java,fangj Exp$
 * created at:上午10:13:00
 */
package com.egf.db.core.define.column.type;

import com.egf.db.core.define.ColumnType;



/**
 * @author fangj
 * @version $Revision: 1.0  $
 * @since 1.0
 */
public class Varchar2 extends ColumnType {
	
	public Varchar2(int length){
		this.length=length;
	}
}
