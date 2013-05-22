/*		
 * Copyright 2013-5-7 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,TableModelImpl.java,fangj Exp$
 * created at:下午03:04:34
 */
package com.egf.db.services.impl;

import com.egf.db.core.define.ColumnType;
import com.egf.db.core.define.column.ColumnDefine;
import com.egf.db.core.define.column.Comment;
import com.egf.db.core.define.column.Default;
import com.egf.db.core.define.column.Limit;
import com.egf.db.core.define.column.NullOrNotNull;
import com.egf.db.core.model.Table;

/**
 * table定义实现
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
class TableImpl implements Table {
	
	public StringBuffer columns=new StringBuffer();
	
	public StringBuffer comments=new StringBuffer();

	public void blob(String name, Comment comment) {
		appendColumn(name, new BlobImpl(), null, null, comment);
	}

	public void blob(String name, NullOrNotNull nullOrNotNull, Comment comment) {
		appendColumn(name, new BlobImpl(), nullOrNotNull, null, comment);
	}

	public void blob(String name, NullOrNotNull nullOrNotNull) {
		appendColumn(name, new BlobImpl(), nullOrNotNull, null, null);
	}

	public void blob(String name) {
		appendColumn(name, new BlobImpl(), null, null, null);
	}

	public void number(String name, Comment comment) {
		appendColumn(name, new NumberImpl(), null, null, comment);
	}

	public void number(String name, Default deft, Comment comment) {
		appendColumn(name, new NumberImpl(), null, deft, null);
	}

	public void number(String name,Default deft,NullOrNotNull nullOrNotNull, Comment comment) {
		appendColumn(name, new NumberImpl(), nullOrNotNull, deft, comment);
	}

	public void number(String name,Default deft,NullOrNotNull nullOrNotNull) {
		appendColumn(name, new NumberImpl(), nullOrNotNull, deft, null);
	}

	public void number(String name, Default deft) {
		appendColumn(name, new NumberImpl(), null, deft, null);
		
	}

	public void number(String name, NullOrNotNull nullOrNotNull,Comment comment) {
		appendColumn(name, new NumberImpl(), nullOrNotNull, null, comment);
	}

	public void number(String name,NullOrNotNull nullOrNotNull) {
		appendColumn(name, new NumberImpl(), nullOrNotNull, null, null);
	}

	public void number(String name) {
		appendColumn(name, new NumberImpl(), null, null, null);
	}

	public void varchar2(String name, Limit limit, Comment comment) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()), null, null, comment);
	}

	public void varchar2(String name, Limit limit, Default deft, Comment comment) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()), null, deft, comment);
	}

	public void varchar2(String name, Limit limit, Default deft,NullOrNotNull nullOrNotNull, Comment comment) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()), nullOrNotNull, deft, comment);
	}

	public void varchar2(String name, Limit limit, Default deft,NullOrNotNull nullOrNotNull) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()), nullOrNotNull, deft, null);
	}

	public void varchar2(String name, Limit limit, Default deft) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()), null, deft, null);
	}

	public void varchar2(String name, Limit limit, NullOrNotNull nullOrNotNull,Comment comment) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()), nullOrNotNull, null, comment);
	}

	public void varchar2(String name, Limit limit, NullOrNotNull nullOrNotNull) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()), nullOrNotNull, null, null);
	}

	public void varchar2(String name, Limit limit) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()), null, null, null);
	}

	
	private void addComment(String columnName,String comment){
		comments.append(String.format("comment on column TN.%s is '%s';\n",columnName, comment));
	}
	
	public void varchar2(String name, Limit limit, ColumnDefine... define) {
		NullOrNotNull nullOrNotNull=null;
		Default deft=null;
		Comment comment=null;
		for (ColumnDefine columnDefine : define) {
			if(columnDefine instanceof NullOrNotNull){
				nullOrNotNull=(NullOrNotNull) columnDefine;
			}else if(columnDefine instanceof Default){
				deft=(Default) columnDefine;
			}else if(columnDefine instanceof Comment){
				comment=(Comment)columnDefine;
			}
		}
		appendColumn(name, new Varchar2Impl(limit.getLimit()), nullOrNotNull, deft, comment);
	}
	
	/**
	 * 添加列
	 * @param name 名称
	 * @param columnType 列类型接口
	 * @param nullOrNotNull 非空接口
	 * @param deft 默认接口
	 * @param comment 注释接口
	 */
	private void appendColumn(String name,ColumnType columnType,NullOrNotNull nullOrNotNull, Default deft,Comment comment){
		String type=columnType.getColumnType();
		columns.append(name);
		columns.append(" ");
		columns.append(type);
		if(deft!=null){
			String deftValue=deft.getValue();
			columns.append(" ");
			columns.append("default");
			columns.append(" ");
			columns.append(deftValue);
		}
		if(nullOrNotNull!=null){
			String nullOrNot=nullOrNotNull.out();
			columns.append(" ");
			columns.append(nullOrNot);
		}if(comment!=null){
			String c=comment.getComment();
			addComment(name, c);
		}
		columns.append(",");
		columns.append("\n");
	}
}
