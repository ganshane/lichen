/*		
 * Copyright 2013-5-24 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DbH2Impl.java,fangj Exp$
 * created at:上午11:34:48
 */
package com.egf.db.core.db;

import java.util.List;

import com.egf.db.core.jdbc.JdbcService;
import com.egf.db.core.jdbc.JdbcServiceImpl;
import com.egf.db.exception.MigrationException;
import com.egf.db.utils.StringUtils;

/**
 * H2数据库实现
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class DbH2Impl extends AbstractDb {
	
    private JdbcService jdbcService=new JdbcServiceImpl();
    
    private final static String PRIMARY_KEY="PRIMARY KEY";
    
	public String[] getPrimaryKeyColumn(String tableName) {
		StringBuffer sql=new StringBuffer("SELECT COLUMN_LIST FROM INFORMATION_SCHEMA.CONSTRAINTS WHERE TABLE_NAME=? AND CONSTRAINT_TYPE=?");
		String name=tableName;
		if(tableName.indexOf(".")!=-1){
			String schema=tableName.split("\\.")[0];
			name=tableName.split("\\.")[1];
			sql.append(" AND TABLE_SCHEMA='"+schema.toUpperCase()+"'");
		}
		List<Object[]> list=(List<Object[]>) jdbcService.find(sql.toString(),new String[]{name.toUpperCase(),PRIMARY_KEY});
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
		StringBuffer sql=new StringBuffer("SELECT CONSTRAINT_NAME FROM INFORMATION_SCHEMA.CONSTRAINTS WHERE TABLE_NAME=? AND CONSTRAINT_TYPE=?");
		if(tableName.indexOf(".")!=-1){
			String schema=tableName.split("\\.")[0];
			name=tableName.split("\\.")[1];
			sql.append(" AND TABLE_SCHEMA='"+schema.toUpperCase()+"'");
		}
		String keyName=(String)jdbcService.unique(sql.toString(), new String[]{name.toUpperCase(),PRIMARY_KEY});
		return keyName;
	}

	
	@Override
	public void createSchema(String schema) throws MigrationException{
		//判断用户是否存在
		String schemaSql="SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME=?";
		String name= (String) jdbcService.unique(schemaSql,new String[]{schema.toUpperCase()});
		if(StringUtils.isBlank(name)){
			String sql="CREATE SCHEMA "+schema.toUpperCase();
			jdbcService.execute(sql);
		}
	}

	public boolean existsTable(String tableName) {
		//判断用户是否存在
		String name=tableName;
		StringBuffer sql=new StringBuffer("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME=?");
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
		StringBuffer sql=new StringBuffer("select DATA_TYPE from information_schema.COLUMNS where TABLE_NAME=? and column_name = ?");
		if(tableName.indexOf(".")!=-1){
			String schema=tableName.split("\\.")[0];
			name=tableName.split("\\.")[1];
			sql.append(" AND TABLE_SCHEMA='"+schema.toUpperCase()+"'");
		}
		String type=(String)jdbcService.unique(sql.toString(), new String[]{name.toUpperCase(),columnName.toUpperCase()});
		return type;
	}
}
