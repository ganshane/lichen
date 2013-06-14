/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,GenerateImpl.java,fangj Exp$
 * created at:上午10:51:19
 */
package com.egf.db.core.sql.template;

import com.egf.db.utils.DateTimeUtils;
import com.egf.db.utils.StringUtils;


/**
 * @author fangj
 * @version $Revision: 2.0.2 $
 * @since 1.0
 */
public class Db2Generate extends AbstractGenerate{
	
	
	public StringBuffer addComment(StringBuffer columnSql, StringBuffer commentSql,String tableName, String columnName, String comment) {
		commentSql.append(String.format("comment on column %s.%s is '%s';",StringUtils.isBlank(tableName)?"TN":tableName, columnName, comment));
		return commentSql;
	}

	public String getString(int length) {
		return "varchar("+length+")";
	}
	
	public String getInteger() {
		return "INTEGER";
	}

	
	public String addColumn(String tableName,String columnName,String columnType,String ... columnDefine) {
		StringBuffer sql= new StringBuffer(String.format("alter table %s add column %s %s;",tableName,columnName,columnType));
		appendSql(sql, tableName, columnName, columnDefine);
		return sql.toString();
	}
	
	
	public String addConstraint(String tableName, String name, String type,String... columnName) {
		String columnNames=StringUtils.getUnionStringArray(columnName,",");
		String sql=String.format("call SYSPROC.ADMIN_CMD('reorg table %s');\nalter table %s add constraint %s %s (%s);",tableName,tableName,name,type,columnNames);
		return sql;
	}
	
	public String changeColumn(String tableName,String columnName,String columnType,String ... columnDefine) {	
		String notNull=(columnDefine!=null&&columnDefine.length>=1)?columnDefine[0]:null;
		String defaultValue=(columnDefine!=null&&columnDefine.length>=2)?columnDefine[1]:null;
		String comment=(columnDefine!=null&&columnDefine.length>=3)?columnDefine[2]:null;
		String unique=(columnDefine!=null&&columnDefine.length>=4)?columnDefine[3]:null;
		String primaryKey=(columnDefine!=null&&columnDefine.length>=5)?columnDefine[4]:null;
		StringBuffer sql= new StringBuffer();		
		if(!StringUtils.isBlank(columnType)){
			sql.append(String.format("alter table %s alter COLUMN %s set data type %s;\n",tableName,columnName,columnType));
		}
		if(!StringUtils.isBlank(defaultValue)){
			sql.append(String.format("alter table %s alter COLUMN %s set default '%s';\n",tableName,columnName,defaultValue));
		}if(!StringUtils.isBlank(notNull)){
			sql.append(String.format("alter table %s alter COLUMN %s set %s;\n",tableName,columnName,notNull));
		}if(!StringUtils.isBlank(comment)){
			addComment(sql,sql,tableName,columnName,comment);
		}if(!StringUtils.isBlank(unique)){
			String uniqueName="unique_"+DateTimeUtils.getNowTimeShortString();
			sql.append("\n"+this.addConstraint(tableName, uniqueName, unique, columnName));
		}if(primaryKey!=null){
			String primaryKeyName="pk_"+DateTimeUtils.getNowTimeShortString();
			sql.append("\n"+this.addConstraint(tableName, primaryKeyName, primaryKey, columnName));
		}
		return sql.toString();
	}
	
	public String renameColumnName(String tableName, String oldColumnName,String newColumnName,String columnType) {
		String sql=String.format("ALTER TABLE %s RENAME COLUMN %s TO %s;",tableName,oldColumnName,newColumnName);
		return sql;
	}
	
	public String dropForeignKey(String tableName, String foreignKeyName) {
		return dropConstraint(tableName, foreignKeyName);
	}
	
	public String dropPrimaryKey(String tableName,String primaryKeyName) {
		return dropConstraint(tableName, primaryKeyName);
	}
	
	public String dropUnique(String tableName, String uniqueName) {
		return dropConstraint(tableName, uniqueName);
	}

	
	public String createSequence(String sequenceName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String dropSequence(String sequenceName){
		// TODO Auto-generated method stub
		return null;
	}
}
