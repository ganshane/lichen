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
	
	public String[] getPrimaryKeyColumn(String tableName) {
		String sql="select column_name from user_cons_columns where constraint_name = (select constraint_name from user_constraints  where table_name = ? and constraint_type = ?)";
		List<String> list=(List<String>) jdbcService.find(sql, new String[]{tableName,"P"});
		if(list!=null&&!list.isEmpty()){
			return (String[])list.toArray();
		}
		return null;
	}

	public String getPrimaryKeyName(String tableName) {
		String sql="select constraint_name from user_constraints  where table_name = ? and constraint_type = ?";
		List<String> list=(List<String>) jdbcService.find(sql, new String[]{tableName,"P"});
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

}
