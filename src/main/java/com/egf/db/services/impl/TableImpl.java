/*		
 * Copyright 2013-5-7 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,TableModelImpl.java,fangj Exp$
 * created at:下午03:04:34
 */
package com.egf.db.services.impl;

import com.egf.db.core.define.column.Comment;
import com.egf.db.core.define.column.Default;
import com.egf.db.core.define.column.Limit;
import com.egf.db.core.define.column.NullOrNotNull;
import com.egf.db.core.model.Table;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
class TableImpl implements Table {
	
	private StringBuffer columns=new StringBuffer();
	
	private StringBuffer comments=new StringBuffer();

	public void blob(String name, Comment comment) {
		// TODO Auto-generated method stub
		
	}

	public void blob(String name, NullOrNotNull nullOrNotNull, Comment comment) {
		// TODO Auto-generated method stub
		
	}

	public void blob(String name, NullOrNotNull nullOrNotNull) {
		// TODO Auto-generated method stub
		
	}

	public void blob(String name) {
		// TODO Auto-generated method stub
		
	}

	public void number(String name, Comment comment) {
		// TODO Auto-generated method stub
		
	}

	public void number(String name, Default deft, Comment comment) {
		// TODO Auto-generated method stub
		
	}

	public void number(String name, Limit limit, Default deft,
			NullOrNotNull nullOrNotNull, Comment comment) {
		// TODO Auto-generated method stub
		
	}

	public void number(String name, Limit limit, Default deft,
			NullOrNotNull nullOrNotNull) {
		// TODO Auto-generated method stub
		
	}

	public void number(String name, Limit limit, Default deft) {
		// TODO Auto-generated method stub
		
	}

	public void number(String name, Limit limit, NullOrNotNull nullOrNotNull,
			Comment comment) {
		// TODO Auto-generated method stub
		
	}

	public void number(String name, Limit limit, NullOrNotNull nullOrNotNull) {
		// TODO Auto-generated method stub
		
	}

	public void number(String name, Limit limit) {
		// TODO Auto-generated method stub
		
	}

	public void varchar2(String name, Limit limit, Comment comment) {
		// TODO Auto-generated method stub
		
	}

	public void varchar2(String name, Limit limit, Default deft, Comment comment) {
		// TODO Auto-generated method stub
		
	}

	public void varchar2(String name, Limit limit, Default deft,
			NullOrNotNull nullOrNotNull, Comment comment) {
		// TODO Auto-generated method stub
		
	}

	public void varchar2(String name, Limit limit, Default deft,
			NullOrNotNull nullOrNotNull) {
		// TODO Auto-generated method stub
		
	}

	public void varchar2(String name, Limit limit, Default deft) {
		// TODO Auto-generated method stub
		
	}

	public void varchar2(String name, Limit limit, NullOrNotNull nullOrNotNull,
			Comment comment) {
		// TODO Auto-generated method stub
		
	}

	public void varchar2(String name, Limit limit, NullOrNotNull nullOrNotNull) {
		// TODO Auto-generated method stub
		
	}

	public void varchar2(String name, Limit limit) {
		// TODO Auto-generated method stub
		
	}


}
