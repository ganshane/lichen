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
		appendColumn(name, new BlobImpl(), comment);
	}

	public void blob(String name, NullOrNotNull nullOrNotNull, Comment comment) {
		appendColumn(name, new BlobImpl(), nullOrNotNull,comment);
	}

	public void blob(String name, NullOrNotNull nullOrNotNull) {
		appendColumn(name, new BlobImpl(), nullOrNotNull);
	}

	public void blob(String name) {
		appendColumn(name, new BlobImpl());
	}

	public void number(String name, Comment comment) {
		appendColumn(name, new NumberImpl(),comment);
	}

	public void number(String name, Default deft, Comment comment) {
		appendColumn(name, new NumberImpl(),deft);
	}

	public void number(String name,Default deft,NullOrNotNull nullOrNotNull, Comment comment) {
		appendColumn(name, new NumberImpl(), nullOrNotNull, deft, comment);
	}
	
	public void number(String name, ColumnDefine... define) {
		appendColumn(name, new NumberImpl(), define);
		
	}

	public void number(String name,Default deft,NullOrNotNull nullOrNotNull) {
		appendColumn(name, new NumberImpl(), nullOrNotNull, deft);
	}

	public void number(String name, Default deft) {
		appendColumn(name, new NumberImpl(),deft);
		
	}

	public void number(String name, NullOrNotNull nullOrNotNull,Comment comment) {
		appendColumn(name, new NumberImpl(), nullOrNotNull, comment);
	}

	public void number(String name,NullOrNotNull nullOrNotNull) {
		appendColumn(name, new NumberImpl(), nullOrNotNull);
	}

	public void number(String name) {
		appendColumn(name, new NumberImpl());
	}

	public void varchar2(String name, Limit limit, Comment comment) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()),comment);
	}

	public void varchar2(String name, Limit limit, Default deft, Comment comment) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()),deft, comment);
	}

	public void varchar2(String name, Limit limit, Default deft,NullOrNotNull nullOrNotNull, Comment comment) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()), nullOrNotNull, deft, comment);
	}

	public void varchar2(String name, Limit limit, Default deft,NullOrNotNull nullOrNotNull) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()), nullOrNotNull, deft);
	}

	public void varchar2(String name, Limit limit, Default deft) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()), deft);
	}

	public void varchar2(String name, Limit limit, NullOrNotNull nullOrNotNull,Comment comment) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()), nullOrNotNull, comment);
	}

	public void varchar2(String name, Limit limit, NullOrNotNull nullOrNotNull) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()), nullOrNotNull);
	}

	public void varchar2(String name, Limit limit) {
		appendColumn(name, new Varchar2Impl(limit.getLimit()));
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
	 * @param columnType 列类型
	 * @param define 列定义
	 */
	private void appendColumn(String name,ColumnType columnType,ColumnDefine ... define){
		String type=columnType.getColumnType();
		columns.append(name);
		columns.append(" ");
		columns.append(type);
		for (ColumnDefine columnDefine : define) {
			if(columnDefine!=null){
				if(columnDefine instanceof Default){
					Default deft=(Default)columnDefine;
					String deftValue=deft.getValue();
					columns.append(" ");
					columns.append("default");
					columns.append(" ");
					columns.append(deftValue);
				}
				if(columnDefine instanceof NullOrNotNull){
					NullOrNotNull nullOrNotNull=(NullOrNotNull)columnDefine;
					String nullOrNot=nullOrNotNull.out();
					columns.append(" ");
					columns.append(nullOrNot);
				}if(columnDefine instanceof Comment){
					Comment comment=(Comment)columnDefine;
					String c=comment.getComment();
					addComment(name, c);
				}
			}
		}
		columns.append(",");
		columns.append("\n");
	}
}
