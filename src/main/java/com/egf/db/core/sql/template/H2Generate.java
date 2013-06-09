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
public class H2Generate extends AbstractGenerate{
	
	public String getString(int length) {
		return "varchar2("+length+")";
	}
	
	public String getInteger() {
		return "number";
	}
	
	public String renameColumnName(String tableName, String oldColumnName,String newColumnName,String columnType) {
		String sql=String.format("ALTER TABLE %s ALTER COLUMN %s RENAME TO %s;", tableName,oldColumnName,newColumnName);
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
	
}
