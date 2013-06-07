/*		
 * Copyright 2013-6-6 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DbDb2Impl.java,fangj Exp$
 * created at:下午04:42:33
 */
package com.egf.db.core.db;

import java.util.List;

import com.egf.db.exception.MigrationException;
import com.egf.db.utils.StringUtils;


/**
 * db2数据库接口实现
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class DbDb2Impl extends AbstractDb{

	public boolean existsTable(String tableName) {
		//判断用户是否存在
		String name=tableName;
		StringBuffer sql=new StringBuffer("select TABLE_NAME from Sysibm.tables where TABLE_TYPE='BASE TABLE' AND TABLE_NAME =?");
		if(tableName.indexOf(".")!=-1){
			String schema=tableName.split("\\.")[0];
			name=tableName.split("\\.")[1];
			sql.append(" AND TABLE_SCHEMA='"+schema.toUpperCase()+"'");
		}
		String tn=(String)jdbcService.unique(sql.toString(), new String[]{name.toUpperCase()});
		return StringUtils.isBlank(tn)?false:true;
	}


	public String getColumnType(String tableName, String columnName) {			
	  return null;
	}

	
	public String[] getPrimaryKeyColumn(String tableName) {
		String name=tableName;
		StringBuffer sql=new StringBuffer("select B.COLNAME from syscat.tabconst A ,SYSCAT.KEYCOLUSE B WHERE A.CONSTNAME = B.CONSTNAME AND A.TYPE='P' and A.tabname =?");
		if(tableName.indexOf(".")!=-1){
			String schema=tableName.split("\\.")[0];
			name=tableName.split("\\.")[1];
			sql.append(" AND A.TABSCHEMA='"+schema.toUpperCase()+"'");
		}
		List<Object[]> list=(List<Object[]>) jdbcService.find(sql.toString(),new String[]{name.toUpperCase()});
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
		StringBuffer sql=new StringBuffer("select B.CONSTNAME from syscat.tabconst A,SYSCAT.KEYCOLUSE B WHERE A.CONSTNAME = B.CONSTNAME AND A.TYPE='P' and A.tabname =? with ur;");
		if(tableName.indexOf(".")!=-1){
			String schema=tableName.split("\\.")[0];
			name=tableName.split("\\.")[1];
			sql.append(" AND TABLE_SCHEMA='"+schema.toUpperCase()+"'");
		}
		String keyName=(String)jdbcService.unique(sql.toString(), new String[]{name.toUpperCase()});
		return keyName;
	}
	
	@Override
	public void createSchema(String schema) throws MigrationException{
		//判断用户是否存在
		String schemaSql="select SCHEMANAME from syscat.schemata where schemaname=?";
		String name= (String) jdbcService.unique(schemaSql,new String[]{schema.toUpperCase()});
		if(StringUtils.isBlank(name)){
			String sql="CREATE SCHEMA "+schema.toUpperCase();
			jdbcService.execute(sql);
		}
	}
	
}
