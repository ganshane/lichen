/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,GenerateImpl.java,fangj Exp$
 * created at:上午10:51:19
 */
package com.egf.db.services.impl;

import com.egf.db.core.sql.template.Generate;
import com.egf.db.utils.StringUtils;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class GenerateImpl implements Generate{

	public String AddConstraint(String tableName, String name, String type,String... columnName) {
		String columnNames=StringUtils.getUnionStringArray(columnName,",");
		String sql=String.format("alter table %s add constraint %s %s (%s);", tableName,name,type,columnNames);
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

	
	public String AddColumn(String tableName, String name, String columnType) {
		String sql= String.format("alter table %s add %s %s;",tableName,name,columnType);
		return sql;
	}
	
	public String AddColumn(String tableName, String name, String columnType,String nullOrNotNull) {
		String sql= String.format("alter table %s add %s %s %s;",tableName,name,columnType,nullOrNotNull);
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

	
	public String dropTalbe(String tableName) {
		String sql= String.format("drop table %s;",tableName);
		return sql;
	}

	
	public String dropConstraint(String talbeName,String name) {
		String sql=String.format("alter table %s drop constraint %s;", talbeName,name);
		return sql;
	}

}
