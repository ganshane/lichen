/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Generate.java,fangj Exp$
 * created at:上午10:40:37
 */
package com.egf.db.core.sql.template;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public interface Generate {
	
	public String AddColumn(String tableName,String name,String columnType);
	
	public String AddColumn(String tableName,String name,String columnType,String nullOrNotNull);
	
	public String addColumnNullOrNot(String tableName,String columnName,String columnType,String nullOrNot);
	
	public String addColumnNull(String tableName, String columnName,String nullOrNot);
	
	public String addDefault(String tableName,String columnName,String value);
	
	public String addComment(String tableName,String columnName,String comment);
	
	public String AddConstraint(String tableName,String name,String type,String ...columnNames);
	
	public String addIndex(String tableName,String indexName,String... columnName);
	
	public String addIndex(String tableName,String indexName,String type,String... columnName);
	
	public String dropTalbe(String tableName);
	
	public String dropColumn(String talbeName,String columnName);
	
	public String dropIndex(String indexName);
	
	public String dropConstraint(String tableName,String name);
	
	
}
