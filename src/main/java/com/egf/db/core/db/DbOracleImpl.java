/*		
 * Copyright 2013-5-24 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DbOracleImpl.java,fangj Exp$
 * created at:上午10:59:20
 */
package com.egf.db.core.db;

import java.util.List;

import com.egf.db.core.jdbc.JdbcService;
import com.egf.db.core.jdbc.JdbcServiceImpl;

/**
 * oracle数据库实现
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class DbOracleImpl implements DbInterface {

	private JdbcService jdbcService=new JdbcServiceImpl();
	
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
		List<Object[]> list=(List<Object[]>) jdbcService.find(sql, new String[]{tableName.split("\\.")[0].toUpperCase(),tableName.split("\\.")[1].toUpperCase(),PRIMARY_KEY});
		if(list!=null&&!list.isEmpty()){
			return (String)list.get(0)[0];
		}
		return null;
	}

}
