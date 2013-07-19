/*		
 * Copyright 2013-5-7 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,TableModelImpl.java,fangj Exp$
 * created at:下午03:04:34
 */
package lichen.migration.services.impl;

import lichen.migration.core.define.ColumnType;
import lichen.migration.core.define.column.ColumnDefine;
import lichen.migration.core.define.column.Comment;
import lichen.migration.core.define.column.Default;
import lichen.migration.core.define.column.Limit;
import lichen.migration.core.define.column.NotNull;
import lichen.migration.core.define.column.PrimaryKey;
import lichen.migration.core.define.column.Unique;
import lichen.migration.core.model.Table;
import lichen.migration.core.sql.template.GenerateFactory;

/**
 * table定义实现
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
class TableImpl implements Table {
	
	public StringBuffer columns=new StringBuffer();
	
	public StringBuffer comments=new StringBuffer();
	
	public void blob(String name, ColumnDefine... columnDefine) {
		appendColumn(name, new BlobImpl(),columnDefine);
	}

	public void integer(String name, ColumnDefine... columnDefine) {
		appendColumn(name, new IntegerImpl(), columnDefine);
	}
	
	public void clob(String name, ColumnDefine... columnDefine) {
		appendColumn(name, new ClobImpl(),columnDefine);
	}
	
	public void date(String name, ColumnDefine... columnDefine) {
		appendColumn(name, new DateImpl(),columnDefine);
	}
	
	public void String(String name, Limit limit, ColumnDefine... columnDefine) {
		appendColumn(name, new StringImpl(limit.getLimit()), columnDefine);
	}
	
	/**
	 * 添加列
	 * @param name 名称
	 * @param columnType 列类型
	 * @param columnDefine 列定义
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
				if(columnDefine instanceof NotNull && primarykey==false){
					columns.append(" ");
					columns.append("not null");
				}if(columnDefine instanceof Comment){
					Comment comment=(Comment)columnDefine;
					String c=comment.getComment();
					GenerateFactory.getGenerate().addComment(columns, comments,null, name, c);
					if(comments.length()!=0){
						comments.append("\n");
					}
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
