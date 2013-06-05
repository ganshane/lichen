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
public class MysqlGenerate extends AbstractGenerate{
	
	public String changeTableComment(String tableName,String comment){
		String sql=String.format("alter table %s comment '%s';",tableName, comment);
		return sql;
	}
	
	public String addColumn(String tableName,String columnName,String columnType,String ... columnDefine) {
		StringBuffer sql= new StringBuffer(String.format("alter table %s add %s %s;",tableName,columnName,columnType));
		appendSql(sql, tableName, columnName, columnDefine);
		return sql.toString();
	}
	
	public String changeColumn(String tableName,String columnName,String columnType,String ... columnDefine) {
		StringBuffer sql= new StringBuffer(String.format("alter table %s modify %s %s;",tableName,columnName,columnType));		
		appendSql(sql, tableName, columnName, columnDefine);
		return sql.toString();
	}	
	

	public String dropColumn(String talbeName, String columnName) {
		String sql= String.format("alter table %s drop column %s;",talbeName,columnName);
		return sql;
	}

	public String dropIndex(String indexName) {
		String tn=indexName.split("\\.")[0];
		String in=indexName.split("\\.")[1];
		String sql= String.format("alter table %s drop index %s;",tn,in);
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
			sql=sql.delete(sql.length()-1, sql.length());
			sql.append(" ");
			sql.append("comment");
			sql.append(" ");
			sql.append("'"+comment+"'");
			sql.append(";");
		}if(!StringUtils.isBlank(unique)){
			String uniqueName="unique_"+DateTimeUtils.getNowTimeShortString();
			sql.append("\n"+this.addConstraint(tableName, uniqueName, unique, columnName));
		}if(primaryKey!=null){
			String primaryKeyName="pk_"+DateTimeUtils.getNowTimeShortString();
			sql.append("\n"+this.addConstraint(tableName, primaryKeyName, primaryKey, columnName));
		}
		return sql.toString();
	}

	public String getNumber() {
		return "int";
	}

	
	public String renameColumnName(String tableName, String oldColumnName,String newColumnName, String columnType) {
		String sql=String.format("ALTER TABLE %s CHANGE %s %s %s",tableName,oldColumnName,newColumnName,columnType);
		return sql;
	}

	
	public StringBuffer addComment(StringBuffer columnSql,StringBuffer commentSql, String columnName, String comment) {
		columnSql.append(" ");
		columnSql.append("comment");
		columnSql.append(" ");
		columnSql.append("'"+comment+"'");
		return columnSql;
	}
	
}
