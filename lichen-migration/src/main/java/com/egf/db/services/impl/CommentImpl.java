/*		
 * Copyright 2013-5-6 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,CommentImpl.java,fangj Exp$
 * created at:上午11:38:01
 */
package com.egf.db.services.impl;

import com.egf.db.core.define.column.Comment;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
class CommentImpl implements Comment{
	
	private String comment;

	public String getComment() {
		return comment;
	}

	public CommentImpl(String comment){
		this.comment=comment;
	}
	
	public String out() {
		return this.getComment();
	}
}
