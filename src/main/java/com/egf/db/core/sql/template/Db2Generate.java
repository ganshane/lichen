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
public class Db2Generate extends AbstractGenerate{
	
	public StringBuffer addComment(StringBuffer columnSql,StringBuffer commentSql, String columnName, String comment) {
		commentSql.append(String.format("comment on column TN.%s is '%s';\n",columnName, comment));
		return commentSql;
	}

	public String getInteger() {
		return "INTEGER";
	}

	public String renameColumnName(String tableName, String oldColumnName,String newColumnName,String columnType) {
		String sql=String.format("ALTER TABLE %s RENAME COLUMN %s TO %s",tableName,oldColumnName,newColumnName);
		return sql;
	}
	
}
