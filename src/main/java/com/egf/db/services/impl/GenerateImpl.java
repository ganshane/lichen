/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,GenerateImpl.java,fangj Exp$
 * created at:上午10:51:19
 */
package com.egf.db.services.impl;

import com.egf.db.core.sql.template.Generate;
import com.egf.db.utils.DateTimeUtils;
import com.egf.db.utils.StringUtils;

/**
 * @author fangj
 * @version $Revision: 2.1 $
 * @since 1.0
 */
public class GenerateImpl implements Generate{
	
	public String addConstraint(String tableName, String name, String type,String... columnName) {
		String columnNames=StringUtils.getUnionStringArray(columnName,",");
		String sql=String.format("alter table %s add constraint %s %s (%s);", tableName,name,type,columnNames);
		return sql;
	}

	public String addForeignKey(String tableName, String name,String referencesTable, String referencesColumn, String... columnName) {
		String columnNames=StringUtils.getUnionStringArray(columnName,",");
		String sql=String.format("alter table %s add constraint %s foreign key (%s) references %s (%s);", tableName,name,columnNames,referencesTable,referencesColumn);
		return sql;
	}

	public String addIndex(String tableName, String indexName,String... columnName) {
		String columnNames=StringUtils.getUnionStringArray(columnName,",");
		String sql= String.format("create index %s on %s (%s);",indexName,tableName,columnNames);
		return sql;
	}

	public String addIndex(String tableName, String indexName, String type,String... columnName) {
		String columnNames=StringUtils.getUnionStringArray(columnName,",");
		String sql= String.format("create %s index %s on %s (%s);",type,indexName,tableName,columnNames);
		return sql;
	}

	
	public String addColumn(String tableName,String columnName,String columnType,String ... columnDefine) {
		StringBuffer sql= new StringBuffer(String.format("alter table %s add %s %s;",tableName,columnName,columnType));
		appendSql(sql, tableName, columnName, columnDefine);
		return sql.toString();
	}
	
	public String changeColumn(String tableName,String columnName,String columnType,String ... columnDefine) {
		StringBuffer sql= new StringBuffer(String.format("alter table %s modify %s;",tableName,columnName));		
		if(StringUtils.isBlank(columnType)&&columnDefine[0]==null&&columnDefine[1]==null){
			return this.addComment(tableName, columnName, columnDefine[2]);
		}else{
			if(!StringUtils.isBlank(columnType)){
				sql=sql.delete(sql.length()-1, sql.length());
				sql.append(" ");
				sql.append(columnType);
				sql.append(";");
			}
			appendSql(sql, tableName, columnName, columnDefine);
		}
		return sql.toString();
	}	
	
	public String addComment(String tableName, String columnName, String comment) {
		String sql= String.format("comment on column %s.%s is '%s';",tableName,columnName,comment);
		return sql;
	}

	public String dropColumn(String talbeName, String columnName) {
		String sql= String.format("alter table %s drop column %s;",talbeName,columnName);
		return sql;
	}

	public String dropIndex(String indexName) {
		String sql= String.format("drop index %s;",indexName);
		return sql;
	}
	
	public String dropTalbe(String tableName) {
		String sql= String.format("drop table %s;",tableName);
		return sql;
	}

	public String dropConstraint(String talbeName,String name) {
		String sql=String.format("alter table %s drop constraint %s;", talbeName,name);
		return sql;
	}
	
	private String appendSql(StringBuffer sql,String tableName,String columnName,String ...columnDefine){
		String notNull=(columnDefine!=null&&columnDefine.length>=1)?columnDefine[0]:null;
		String defaultValue=(columnDefine!=null&&columnDefine.length>=2)?columnDefine[1]:null;
		String comment=(columnDefine!=null&&columnDefine.length>=3)?columnDefine[2]:null;
		String unique=(columnDefine!=null&&columnDefine.length>=4)?columnDefine[3]:null;
		String primaryKey=(columnDefine!=null&&columnDefine.length>=5)?columnDefine[4]:null;
		if(!StringUtils.isBlank(defaultValue)){
			sql=sql.delete(sql.length()-1, sql.length());
			sql.append(" ");
			String value=defaultValue;
			sql.append("default");
			sql.append(" ");
			sql.append("'");
			sql.append(value);
			sql.append("'");
			sql.append(";");
		}if(!StringUtils.isBlank(notNull)){
			sql=sql.delete(sql.length()-1, sql.length());
			sql.append(" ");
			sql.append(notNull);
			sql.append(";");
		}if(!StringUtils.isBlank(comment)){
			sql.append("\n"+this.addComment(tableName, columnName, comment));
		}if(!StringUtils.isBlank(unique)){
			String uniqueName="unique_"+DateTimeUtils.getNowTimeShortString();
			sql.append("\n"+this.addConstraint(tableName, uniqueName, unique, columnName));
		}if(primaryKey!=null){
			String primaryKeyName="pk_"+DateTimeUtils.getNowTimeShortString();
			sql.append("\n"+this.addConstraint(tableName, primaryKeyName, primaryKey, columnName));
		}
		return sql.toString();
	}

	
	public String renameColumn(String tableName, String oldColumnName,String newColumnName) {
		String sql=String.format("alter table %s rename column %s to %s;", tableName,oldColumnName,newColumnName);
		return sql;
	}

}
