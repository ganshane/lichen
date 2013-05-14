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
	
	public StringBuffer columns=new StringBuffer();
	
	public StringBuffer comments=new StringBuffer();

	public void blob(String name, Comment comment) {
		CommentImpl commentImpl=(CommentImpl)comment;
		columns.append(name);
		columns.append(" ");
		columns.append("bolb");
		columns.append(",");
		columns.append("\n");
		addComment(name, commentImpl.getComment());
	}

	public void blob(String name, NullOrNotNull nullOrNotNull, Comment comment) {
		CommentImpl commentImpl=(CommentImpl)comment;
		String nullOrNot=nullOrNotNull.out();
		columns.append(name);
		columns.append(" ");
		columns.append("bolb");
		columns.append(" ");
		columns.append(nullOrNot);
		columns.append(",");
		columns.append("\n");
		addComment(name, commentImpl.getComment());
	}

	public void blob(String name, NullOrNotNull nullOrNotNull) {
		String nullOrNot=nullOrNotNull.out();
		columns.append(name);
		columns.append(" ");
		columns.append("bolb");
		columns.append(" ");
		columns.append(nullOrNot);
		columns.append(",");
		columns.append("\n");
	}

	public void blob(String name) {
		columns.append(name);
		columns.append(" ");
		columns.append("bolb");
		columns.append(",");
		columns.append("\n");
	}

	public void number(String name, Comment comment) {
		CommentImpl commentImpl=(CommentImpl)comment;
		columns.append(name);
		columns.append(" ");
		columns.append("number");
		columns.append(",");
		columns.append("\n");
		addComment(name, commentImpl.getComment());
	}

	public void number(String name, Default deft, Comment comment) {
		DefaultImpl defaultImpl=(DefaultImpl)deft;
		CommentImpl commentImpl=(CommentImpl)comment;
		columns.append(name);
		columns.append(" ");
		columns.append("number");
		columns.append(" ");
		columns.append("default");
		columns.append(" ");
		columns.append(defaultImpl.getValue());
		columns.append(",");
		columns.append("\n");
		addComment(name, commentImpl.getComment());
	}

	public void number(String name,Default deft,NullOrNotNull nullOrNotNull, Comment comment) {
		DefaultImpl defaultImpl=(DefaultImpl)deft;
		CommentImpl commentImpl=(CommentImpl)comment;
		String nullOrNot=nullOrNotNull.out();
		columns.append(name);
		columns.append(" ");
		columns.append("number");
		columns.append(" ");
		columns.append("default");
		columns.append(" ");
		columns.append(defaultImpl.getValue());
		columns.append(" ");
		columns.append(nullOrNot);
		columns.append(",");
		columns.append("\n");
		addComment(name, commentImpl.getComment());
	}

	public void number(String name,Default deft,NullOrNotNull nullOrNotNull) {
		DefaultImpl defaultImpl=(DefaultImpl)deft;
		String nullOrNot=nullOrNotNull.out();
		columns.append(name);
		columns.append(" ");
		columns.append("number");
		columns.append(" ");
		columns.append("default");
		columns.append(" ");
		columns.append(defaultImpl.getValue());
		columns.append(" ");
		columns.append(nullOrNot);
		columns.append(",");
		columns.append("\n");
	}

	public void number(String name, Default deft) {
		DefaultImpl defaultImpl=(DefaultImpl)deft;;
		columns.append(name);
		columns.append(" ");
		columns.append("number");
		columns.append(" ");
		columns.append("default");
		columns.append(" ");
		columns.append(defaultImpl.getValue());
		columns.append(",");
		columns.append("\n");
		
	}

	public void number(String name, NullOrNotNull nullOrNotNull,Comment comment) {
		String nullOrNot=nullOrNotNull.out();
		CommentImpl commentImpl=(CommentImpl)comment;
		columns.append(name);
		columns.append(" ");
		columns.append("number");
		columns.append(" ");
		columns.append(nullOrNot);
		columns.append(",");
		columns.append("\n");
		addComment(name, commentImpl.getComment());
	}

	public void number(String name,NullOrNotNull nullOrNotNull) {
		String nullOrNot=nullOrNotNull.out();
		columns.append(name);
		columns.append(" ");
		columns.append("number");
		columns.append(" ");
		columns.append(nullOrNot);
		columns.append(",");
		columns.append("\n");
	}

	public void number(String name) {
		columns.append(name);
		columns.append(" ");
		columns.append("number");
		columns.append(",");
		columns.append("\n");
	}

	public void varchar2(String name, Limit limit, Comment comment) {
		LimitImpl limitImpl=(LimitImpl)limit;
		CommentImpl commentImpl=(CommentImpl)comment;
		columns.append(name);
		columns.append(" ");
		columns.append("varchar2(");
		columns.append(limitImpl.getLimit());
		columns.append(")");
		columns.append(",");
		columns.append("\n");
		addComment(name, commentImpl.getComment());
	}

	public void varchar2(String name, Limit limit, Default deft, Comment comment) {
		LimitImpl limitImpl=(LimitImpl)limit;
		DefaultImpl defaultImpl=(DefaultImpl)deft;
		CommentImpl commentImpl=(CommentImpl)comment;
		String value=defaultImpl.getValue();
		columns.append(name);
		columns.append(" ");
		columns.append("varchar2(");
		columns.append(limitImpl.getLimit());
		columns.append(")");
		columns.append(" ");
		columns.append("default");
		columns.append(" ");
		columns.append(value);
		columns.append(",");
		columns.append("\n");
		addComment(name, commentImpl.getComment());
	}

	public void varchar2(String name, Limit limit, Default deft,NullOrNotNull nullOrNotNull, Comment comment) {
		LimitImpl limitImpl=(LimitImpl)limit;
		DefaultImpl defaultImpl=(DefaultImpl)deft;
		CommentImpl commentImpl=(CommentImpl)comment;
		String nullOrNot=nullOrNotNull.out();
		String value=defaultImpl.getValue();
		columns.append(name);
		columns.append(" ");
		columns.append("varchar2(");
		columns.append(limitImpl.getLimit());
		columns.append(")");
		columns.append(" ");
		columns.append("default");
		columns.append(" ");
		columns.append(value);
		columns.append(" ");
		columns.append(nullOrNot);
		columns.append(",");
		columns.append("\n");
		addComment(name, commentImpl.getComment());
	}

	public void varchar2(String name, Limit limit, Default deft,NullOrNotNull nullOrNotNull) {
		LimitImpl limitImpl=(LimitImpl)limit;
		DefaultImpl defaultImpl=(DefaultImpl)deft;
		String nullOrNot=nullOrNotNull.out();
		String value=defaultImpl.getValue();
		columns.append(name);
		columns.append(" ");
		columns.append("varchar2(");
		columns.append(limitImpl.getLimit());
		columns.append(")");
		columns.append(" ");
		columns.append("default");
		columns.append(" ");
		columns.append(value);
		columns.append(" ");
		columns.append(nullOrNot);
		columns.append(",");
		columns.append("\n");
	}

	public void varchar2(String name, Limit limit, Default deft) {
		LimitImpl limitImpl=(LimitImpl)limit;
		DefaultImpl defaultImpl=(DefaultImpl)deft;
		String value=defaultImpl.getValue();
		columns.append(name);
		columns.append(" ");
		columns.append("varchar2(");
		columns.append(limitImpl.getLimit());
		columns.append(")");
		columns.append(" ");
		columns.append("default");
		columns.append(" ");
		columns.append(value);
		columns.append(",");
		columns.append("\n");
	}

	public void varchar2(String name, Limit limit, NullOrNotNull nullOrNotNull,Comment comment) {
		LimitImpl limitImpl=(LimitImpl)limit;
		CommentImpl commentImpl=(CommentImpl)comment;
		String nullOrNot=nullOrNotNull.out();
		columns.append(name);
		columns.append(" ");
		columns.append("varchar2(");
		columns.append(limitImpl.getLimit());
		columns.append(")");
		columns.append(" ");
		columns.append(nullOrNot);
		columns.append(",");
		columns.append("\n");
		addComment(name, commentImpl.getComment());
	}

	public void varchar2(String name, Limit limit, NullOrNotNull nullOrNotNull) {
		LimitImpl limitImpl=(LimitImpl)limit;
		String nullOrNot=nullOrNotNull.out();
		columns.append(name);
		columns.append(" ");
		columns.append("varchar2(");
		columns.append(limitImpl.getLimit());
		columns.append(")");
		columns.append(" ");
		columns.append(nullOrNot);
		columns.append(",");
		columns.append("\n");
		
	}

	public void varchar2(String name, Limit limit) {
		LimitImpl limitImpl=(LimitImpl)limit;
		columns.append(name);
		columns.append(" ");
		columns.append("varchar2(");
		columns.append(limitImpl.getLimit());
		columns.append(")");
		columns.append(",");
		columns.append("\n");
	}

	
	private void addComment(String columnName,String comment){
		comments.append(String.format("comment on column TN.%s is '%s';\n",columnName, comment));
	}

}
