/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Table.java,fangj Exp$
 * created at:下午12:44:32
 */
package com.egf.db.core.model;

import java.sql.SQLException;

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
	
	/**
	 * varchar2
	 * @param name 名称
	 * @param limit 长度限制
	 * @param notNull 不为空
	 */
	public void varchar2(String name,Limit limit,NotNull notNull);
	
	/**
	 * varchar2
	 * @param name 名称
	 * @param limit 长度限制
	 * @param notNull 不为空
	 * @param comment 注释
	 */
	public void varchar2(String name,Limit limit,NotNull notNull,Comment comment);
	
	/**
	 * varchar2
	 * @param name 名称
	 * @param limit 长度限制
	 * @param comment 注释
	 */
	public void varchar2(String name,Limit limit,Comment comment);
	
	/**
	 * varchar2
	 * @param name 名称
	 * @param limit 长度限制
	 */
	public void varchar2(String name,Limit limit);
	
	/**
	 * varchar2
	 * @param name 名称
	 * @param limit 长度限制
	 * @param deft	默认值
	 * @param notNull 不为空
	 */
	public void varchar2(String name,Limit limit,Default deft,NotNull notNull);
	
	/**
	 * varchar2
	 * @param name 名称
	 * @param limit 长度限制
	 * @param deft 默认值
	 * @param notNull 不为空
	 * @param comment 注释
	 */
	public void varchar2(String name,Limit limit,Default deft,NotNull notNull,Comment comment);
	
	/**
	 * varchar2
	 * @param name 名称
	 * @param limit 长度限制
	 * @param deft 默认值
	 * @param comment 注释
	 */
	public void varchar2(String name,Limit limit,Default deft,Comment comment);
	
	/**
	 * varchar2
	 * @param name 名称
	 * @param limit 长度限制
	 * @param deft 默认值
	 */
	public void varchar2(String name,Limit limit,Default deft);
	
	/**
	 * varchar2类型
	 * @param name 字段名称
	 * @param limit 长度
	 * @param define 其他字段特性定义
	 * 可选参数：
	 * Default 默认值
	 * Null,NotNull 是否可为空 
	 * Comment 注释
	 * @throws SQLException
	 */
	public void varchar2(String name,Limit limit, ColumnDefine ... define);
	
	/**
	 * blob
	 * @param name 名称
	 * @param notNull 不为空
	 */
	public void blob(String name,NotNull notNull);
	
	/**
	 * blob
	 * @param name 名称
	 */
	public void blob(String name);
	
	/**
	 * blob
	 * @param name 名称
	 * @param notNull 不为空
	 * @param comment 注释
	 */
	public void blob(String name,NotNull notNull,Comment comment);
	
	/**
	 * blob
	 * @param name 名称
	 * @param comment 注释
	 */
	public void blob(String name,Comment comment);
	
	/**
	 * number
	 * @param name 名称
	 * @param notNull 不为空
	 */
	public void number(String name,NotNull notNull);
	
	/**
	 * number 
	 * @param name 名称
	 */
	public void number(String name);
	
	/**
	 * number
	 * @param name 名称
	 * @param notNull 不为空
	 * @param comment 注释
	 */
	public void number(String name,NotNull notNull,Comment comment);
	
	/**
	 * number
	 * @param name 名称
	 * @param comment 注释
	 */
	public void number(String name,Comment comment);
	
	/**
	 * number
	 * @param name 名称
	 * @param deft 默认值
	 * @param notNull 不为空
	 */
	public void number(String name,Default deft, NotNull notNull);
	
	/**
	 * number
	 * @param name 名称
	 * @param deft 默认值
	 */
	public void number(String name,Default deft);
	
	/**
	 * number
	 * @param name 名称
	 * @param deft 默认值
	 * @param notNull 不为空
	 * @param comment 注释
	 */
	public void number(String name,Default deft,NotNull notNull,Comment comment);
	
	/**
	 * number
	 * @param name 名称
	 * @param define 其他字段特性定义
	 * 可选参数：
	 * Default 默认值
	 * Null,NotNull 是否可为空 
	 * Comment 注释
	 */
	public void number(String name,ColumnDefine ... define);
	
	/**
	 * number 
	 * @param name 名称
	 * @param deft 默认值
	 * @param comment 注释
	 */
	public void number(String name,Default deft,Comment comment);
}
