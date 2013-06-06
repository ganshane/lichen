/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Table.java,fangj Exp$
 * created at:下午12:44:32
 */
package com.egf.db.core.model;

import java.sql.SQLException;

import com.egf.db.core.define.column.ColumnDefine;
import com.egf.db.core.define.column.Limit;


/**
 * 表模型
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public interface Table {
	
	/**
	 * 字符串类型
	 * @param name 字段名称
	 * @param limit 长度
	 * @param define 其他字段特性定义(可多选)<br>
	 * 可选参数：<br>
	 * Default 默认值<br>
	 * Null,NotNull 是否可为空 <br>
	 * Comment 注释<br>
	 * PrimaryKey 是否是主键<br>
	 * Unique 唯一
	 * @throws SQLException
	 */
	public void String(String name,Limit limit, ColumnDefine ... define);

	
	/**
	 * 整数 
	 * @param name 名称
	 * @param define 其他字段特性定义(可多选)<br>
	 * 可选参数：<br>
	 * Default 默认值<br>
	 * Null,NotNull 是否可为空 <br>
	 * Comment 注释<br>
	 * PrimaryKey 是否是主键<br>
	 * Unique 唯一
	 */
	public void integer(String name,ColumnDefine ... define);

	
	/**
	 * 时间
	 * @param name 名称
	 * @param define 其他字段特性定义(可多选)<br>
	 * 可选参数：<br>
	 * Default 默认值<br>
	 * Null,NotNull 是否可为空 <br>
	 * Comment 注释
	 */
	public void date(String name,ColumnDefine ... define);
	
	
	/**
	 * blob 大字段
	 * @param name 名称
	 * @param define 其他字段特性定义(可多选)<br>
	 * 可选参数：<br>
	 * Null,NotNull 是否可为空 <br>
	 * Comment 注释
	 */
	public void blob(String name,ColumnDefine ... define);
	
	
	/**
	 * clob 大字段
	 * @param name 名称
	 * @param define 其他字段特性定义(可多选)<br>
	 * 可选参数：<br>
	 * Null,NotNull 是否可为空 <br>
	 * Comment 注释
	 */
	public void clob(String name,ColumnDefine ... define);
	
}
