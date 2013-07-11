/*		
 * Copyright 2013-5-24 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DbOracleImpl.java,fangj Exp$
 * created at:上午10:59:20
 */
package com.egf.db.core.db;

import java.util.List;

import com.egf.db.utils.StringUtils;

/**
 * oracle数据库实现
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class DbOracleImpl extends AbstractDb{
	
	private final static String PRIMARY_KEY="P";
	
	public String[] getPrimaryKeyColumn(String tableName) {
		String sql="select column_name from all_cons_columns where owner =? and constraint_name = (select constraint_name from all_constraints where owner=? and table_name = ? and constraint_type = ?)";
		List<Object[]> list=(List<Object[]>) jdbcService.find(sql, new String[]{tableName.split("\\.")[0].toUpperCase(),tableName.split("\\.")[0].toUpperCase(),tableName.split("\\.")[1].toUpperCase(),PRIMARY_KEY});
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
		String sql="select constraint_name from all_constraints where owner = ? and table_name = ? and constraint_type = ?";
		String  keyName=(String) jdbcService.unique(sql, new String[]{tableName.split("\\.")[0].toUpperCase(),tableName.split("\\.")[1].toUpperCase(),PRIMARY_KEY});
		return keyName;
	}
	
	public boolean existsTable(String tableName) {
		//判断用户是否存在
		String name=tableName;
		StringBuffer sql=new StringBuffer("select table_name from all_all_tables where table_name=?");
		if(tableName.indexOf(".")!=-1){
			String schema=tableName.split("\\.")[0];
			name=tableName.split("\\.")[1];
			sql.append(" AND owner='"+schema.toUpperCase()+"'");
		}
		String tn=(String)jdbcService.unique(sql.toString(), new String[]{name.toUpperCase()});
		return StringUtils.isBlank(tn)?false:true;
	}

	public String getColumnType(String tableName, String columnName) {
		return null;
	}

	
	public boolean columnIsNotNull(String tableName, String columnName) {
		StringBuffer sql=new StringBuffer("select nullable from all_tab_columns where  table_name=? and column_name=?");
		String name=tableName;
		if(tableName.indexOf(".")!=-1){
			String schema=tableName.split("\\.")[0];
			name=tableName.split("\\.")[1];
			sql.append(" AND owner='"+schema.toUpperCase()+"'");
		}
		String nullable=(String)jdbcService.unique(sql.toString(), new String[]{name.toUpperCase(),columnName.toUpperCase()});
		return "N".equals(nullable)?true:false;
	}

}
