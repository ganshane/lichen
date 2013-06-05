/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,GenerateImpl.java,fangj Exp$
 * created at:上午10:51:19
 */
package com.egf.db.core.sql.template;


/**
 * @author fangj
 * @version $Revision: 2.1 $
 * @since 1.0
 */
public class H2Generate extends AbstractGenerate{
	
	public String getNumber() {
		return "number";
	}
	
	public String renameColumnName(String tableName, String oldColumnName,String newColumnName,String columnType) {
		String sql=String.format("ALTER TABLE %s ALTER COLUMN %s RENAME TO %s", tableName,oldColumnName,newColumnName);
		return sql;
	}
	
}
