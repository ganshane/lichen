/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,GenerateImpl.java,fangj Exp$
 * created at:上午10:51:19
 */
package com.egf.db.core.sql.template;

import com.egf.db.utils.StringUtils;

/**
 * @author fangj
 * @version $Revision: 2.1 $
 * @since 1.0
 */
public class OracleGenerate extends AbstractGenerate{
	
	public String addIndex(String tableName, String indexName,String... columnName) {
		String columnNames=StringUtils.getUnionStringArray(columnName,",");
		String sql= String.format("create index %s on %s (%s);",indexName,tableName,columnNames);
		return sql;
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

	public String getString(int length) {
		return "varchar2("+length+")";
	}
	
	public String getInteger() {
		return "number";
	}
	
	public String renameColumnName(String tableName, String oldColumnName,String newColumnName,String columnType) {
		String sql=String.format("ALTER TABLE %s RENAME COLUMN %s TO %s;",tableName,oldColumnName,newColumnName);
		return sql;
	}
	
	public StringBuffer addComment(StringBuffer columnSql, StringBuffer commentSql,String tableName, String columnName, String comment) {
		commentSql.append(String.format("comment on column %s.%s is '%s';",StringUtils.isBlank(tableName)?"TN":tableName, columnName, comment));
		return commentSql;
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
		String sql=String.format("create sequence %s", sequenceName);
		return sql;
	}

	public String dropSequence(String sequenceName) {
		String sql=String.format("DROP SEQUENCE %s", sequenceName);
		return sql;
	}
}
