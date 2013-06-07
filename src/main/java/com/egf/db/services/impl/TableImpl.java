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
import com.egf.db.core.define.column.NotNull;
import com.egf.db.core.define.column.PrimaryKey;
import com.egf.db.core.define.column.Unique;
import com.egf.db.core.model.Table;
import com.egf.db.core.sql.template.GenerateFactory;

/**
 * table定义实现
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
class TableImpl implements Table {
	
	public StringBuffer columns=new StringBuffer();
	
	public StringBuffer comments=new StringBuffer();
	
	public void blob(String name, ColumnDefine... define) {
		appendColumn(name, new BlobImpl(),define);
	}

	public void integer(String name, ColumnDefine... define) {
		appendColumn(name, new IntegerImpl(), define);
	}
	
	public void clob(String name, ColumnDefine... define) {
		appendColumn(name, new ClobImpl(),define);
	}
	
	public void date(String name, ColumnDefine... define) {
		appendColumn(name, new DateImpl(),define);
	}
	
	public void String(String name, Limit limit, ColumnDefine... define) {
		appendColumn(name, new StringImpl(limit.getLimit()), define);
	}
	
	/**
	 * 添加列
	 * @param name 名称
	 * @param columnType 列类型
	 * @param define 列定义
	 */
	private void appendColumn(String name,ColumnType columnType,ColumnDefine ... define){
		String type=columnType.getColumnType();
		boolean primarykey=false;
		columns.append(name);
		columns.append(" ");
		columns.append(type);
		for (ColumnDefine columnDefine : define) {
			if(columnDefine instanceof PrimaryKey){
				primarykey=true;
				break;
			}
		}
		for (ColumnDefine columnDefine : define) {
			if(columnDefine!=null){
				if(columnDefine instanceof Default && primarykey==false){
					Default deft=(Default)columnDefine;
					String deftValue=deft.getValue();
					columns.append(" ");
					columns.append("default");
					columns.append(" ");
					columns.append(deftValue);
				}
				if(columnDefine instanceof NotNull){
					columns.append(" ");
					columns.append("not null");
				}if(columnDefine instanceof Comment){
					Comment comment=(Comment)columnDefine;
					String c=comment.getComment();
					GenerateFactory.getGenerate().addComment(columns, comments,null, name, c);
					comments.append("\n");
				}if(columnDefine instanceof Unique && primarykey==false){
					columns.append(" ");
					columns.append("unique");
				}if(columnDefine instanceof PrimaryKey){
					columns.append(" ");
					columns.append("not null primary key");
				}
			}
		}
		columns.append(",");
		columns.append("\n");
	}

}
