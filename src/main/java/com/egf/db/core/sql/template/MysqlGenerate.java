/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,GenerateImpl.java,fangj Exp$
 * created at:上午10:51:19
 */
package com.egf.db.core.sql.template;


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

	public String dropColumn(String talbeName, String columnName) {
		String sql= String.format("alter table %s drop column %s;",talbeName,columnName);
		return sql;
	}

	public String dropIndex(String indexName) {
		String tn=indexName.substring(0,indexName.lastIndexOf("."));
		String in=indexName.substring(indexName.lastIndexOf(".")+1);
		String sql= String.format("alter table %s drop index %s;",tn,in);
		return sql;
	}
	
	public String dropPrimaryKey(String talbeName,String primaryKeyName) {
		String sql=String.format("alter table %s drop primary key;", talbeName);
		return sql;
	}
	
	public String dropForeignKey(String talbeName,String foreignKeyName){
		String sql=String.format("alter table %s drop foreign key %s;", talbeName,foreignKeyName);
		return sql;
	}
	
	public String dropUnique(String tableName,String uniqueName){
		String sql=String.format("alter table %s drop index %s;", tableName,uniqueName);
		return sql;
	}
	
	public String getString(int length) {
		return "varchar("+length+")";
	}

	public String getInteger() {
		return "int";
	}
	
	public String renameColumnName(String tableName, String oldColumnName,String newColumnName, String columnType) {
		String sql=String.format("ALTER TABLE %s CHANGE %s %s %s;",tableName,oldColumnName,newColumnName,columnType);
		return sql;
	}
	
	public StringBuffer addComment(StringBuffer columnSql,StringBuffer commentSql,String tableName,String columnName, String comment) {
		boolean flag=false;
		if(";\n".equals(columnSql.substring(columnSql.length()-2, columnSql.length()))){
			columnSql=columnSql.delete(columnSql.length()-2, columnSql.length());
			flag=true;
		}
		columnSql.append(" ");
		columnSql.append("comment");
		columnSql.append(" ");
		columnSql.append("'"+comment+"'");
		if(flag){
			columnSql.append(";");
		}
		return columnSql;
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
