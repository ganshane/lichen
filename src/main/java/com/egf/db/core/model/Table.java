/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Table.java,fangj Exp$
 * created at:下午12:44:32
 */
package com.egf.db.core.model;

import com.egf.db.core.define.column.ColumnDefine;
import com.egf.db.core.define.column.Comment;
import com.egf.db.core.define.column.Default;
import com.egf.db.core.define.column.Limit;
import com.egf.db.core.define.column.NotNull;


/**
 * 表模型
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public interface Table {
		
	public void varchar2(String name,Limit limit,NotNull notNull);
	
	public void varchar2(String name,Limit limit,NotNull notNull,Comment comment);
	
	public void varchar2(String name,Limit limit,Comment comment);
	
	public void varchar2(String name,Limit limit);
	
	public void varchar2(String name,Limit limit,Default deft,NotNull notNull);
	
	public void varchar2(String name,Limit limit,Default deft,NotNull notNull,Comment comment);
	
	public void varchar2(String name,Limit limit,Default deft,Comment comment);
	
	public void varchar2(String name,Limit limit,Default deft);
	
	/**
	 * varchar2类型
	 * @param name 字段名称
	 * @param limit 长度
	 * @param define 其他字段特性定义(NullOrNotNull,Default,Comment)
	 */
	public void varchar2(String name,Limit limit, ColumnDefine ... define);
	
	public void blob(String name,NotNull notNull);
	
	public void blob(String name);
	
	public void blob(String name,NotNull notNull,Comment comment);
	
	public void blob(String name,Comment comment);
	
	public void number(String name,NotNull notNull);
	
	public void number(String name);
	
	public void number(String name,NotNull notNull,Comment comment);
	
	public void number(String name,Comment comment);
	
	public void number(String name,Default deft, NotNull notNull);
	
	public void number(String name,Default deft);
	
	public void number(String name,Default deft,NotNull notNull,Comment comment);
	
	public void number(String name,ColumnDefine ... define);
	
	public void number(String name,Default deft,Comment comment);
}
