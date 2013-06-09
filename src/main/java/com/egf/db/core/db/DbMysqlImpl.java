/*		
 * Copyright 2013-5-24 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DbMysqlImpl.java,fangj Exp$
 * created at:下午03:21:14
 */
package com.egf.db.core.db;

import java.util.List;

import com.egf.db.exception.MigrationException;
import com.egf.db.utils.StringUtils;

/**
 * mysql数据库实现
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class DbMysqlImpl extends AbstractDb {

	private final static String PRIMARY_KEY="PRIMARY KEY";
    
	public String[] getPrimaryKeyColumn(String tableName) {
		String name=tableName;
		StringBuffer sql=new StringBuffer("SELECT k.COLUMN_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS t LEFT JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE k USING(CONSTRAINT_NAME,TABLE_SCHEMA,TABLE_NAME) WHERE t.CONSTRAINT_TYPE=? AND t.TABLE_NAME=?");
		if(tableName.indexOf(".")!=-1){
			String schema=tableName.split("\\.")[0];
			name=tableName.split("\\.")[1];
			sql.append(" AND TABLE_SCHEMA='"+schema.toUpperCase()+"'");
		}
		List<Object[]> list=(List<Object[]>) jdbcService.find(sql.toString(),new String[]{PRIMARY_KEY,name.toUpperCase()});
		if(list!=null&&!list.isEmpty()){
			String[] strings = new String[ list.size()];
			for (int i = 0; i < list.size(); i++) {
				strings[i]=(String)list.get(i)[0];
			}
			return strings;
		}
		return null;
	}
	
	public String getPrimaryKeyName(String tableName) {
		String name=tableName;
		StringBuffer sql=new StringBuffer("SELECT t.CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS t LEFT JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE k USING(CONSTRAINT_NAME,TABLE_SCHEMA,TABLE_NAME) WHERE t.CONSTRAINT_TYPE=? AND t.TABLE_NAME=?");
		if(tableName.indexOf(".")!=-1){
			String schema=tableName.split("\\.")[0];
			name=tableName.split("\\.")[1];
			sql.append(" AND TABLE_SCHEMA='"+schema.toUpperCase()+"'");
		}
		String keyName=(String) jdbcService.unique(sql.toString(), new String[]{PRIMARY_KEY,name.toUpperCase()});
		return keyName;
	}
	
	@Override
	public void createSchema(String schema) throws MigrationException{
		//判断用户是否存在
		String schemaSql="SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME=?";
		String name= (String) jdbcService.unique(schemaSql,new String[]{schema.toUpperCase()});
		if(StringUtils.isBlank(name)){
			String sql="CREATE database "+schema.toUpperCase();
			jdbcService.execute(sql);
		}
	}
	
	public boolean existsTable(String tableName) {
		//判断用户是否存在
		String name=tableName;
		StringBuffer sql=new StringBuffer("select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME=?");
		if(tableName.indexOf(".")!=-1){
			String schema=tableName.split("\\.")[0];
			name=tableName.split("\\.")[1];
			sql.append(" AND TABLE_SCHEMA='"+schema.toUpperCase()+"'");
		}
		String tn=(String)jdbcService.unique(sql.toString(), new String[]{name.toUpperCase()});
		return StringUtils.isBlank(tn)?false:true;
	}

	public String getColumnType(String tableName, String columnName) {
		String name=tableName;
		StringBuffer sql=new StringBuffer("select DATA_TYPE,character_maximum_length from information_schema.COLUMNS where TABLE_NAME=? and column_name = ?");
		if(tableName.indexOf(".")!=-1){
			String schema=tableName.split("\\.")[0];
			name=tableName.split("\\.")[1];
			sql.append(" AND TABLE_SCHEMA='"+schema.toUpperCase()+"'");
		}
		Object[] objects=(Object[])jdbcService.unique(sql.toString(), new String[]{name.toUpperCase(),columnName.toUpperCase()});
		if(objects[1]==null||StringUtils.isBlank(objects[1].toString())){
			return (String)objects[0];
		}else{
			return objects[0]+"("+objects[1]+")";
		}
	}
	
	public boolean columnIsNotNull(String tableName, String columnName) {
		StringBuffer sql=new StringBuffer("select is_nullable from information_schema.COLUMNS where TABLE_NAME=? and column_name =?");
		String name=tableName;
		if(tableName.indexOf(".")!=-1){
			String schema=tableName.split("\\.")[0];
			name=tableName.split("\\.")[1];
			sql.append(" AND owner='"+schema.toUpperCase()+"'");
		}
		String nullable=(String)jdbcService.unique(sql.toString(), new String[]{name.toUpperCase(),columnName.toUpperCase()});
		return "NO".equals(nullable)?true:false;
	}
	
}
