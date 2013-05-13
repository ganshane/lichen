/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Table.java,fangj Exp$
 * created at:下午12:44:32
 */
package com.egf.db.core.model;

import com.egf.db.core.define.column.Comment;
import com.egf.db.core.define.column.Limit;
import com.egf.db.core.define.column.NullOrNotNull;


/**
 * 表模型
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public interface Table {
		
	public void varchar2(String name,Limit limit,NullOrNotNull nullOrNotNull);
	
	public void varchar2(String name,Limit limit,NullOrNotNull nullOrNotNull,Comment comment);
	
	public void varchar2(String name,Limit limit,Comment comment);
	
	public void varchar2(String name,Limit limit);
	
	
	
	public void blob(String name,NullOrNotNull nullOrNotNull);
	
	public void blob(String name);
	
	public void blob(String name,NullOrNotNull nullOrNotNull,Comment comment);
	
	public void blob(String name,Comment comment);
	
	
	
	public void number(String name,Limit limit,NullOrNotNull nullOrNotNull);
	
	public void number(String name,Limit limit);
	
	public void number(String name,Limit limit,NullOrNotNull nullOrNotNull,Comment comment);
	
	public void number(String name,Comment comment);
	
}
