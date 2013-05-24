/*		
 * Copyright 2013-5-24 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DbMysqlImpl.java,fangj Exp$
 * created at:下午03:21:14
 */
package com.egf.db.core.db;

import java.util.List;

import com.egf.db.core.jdbc.JdbcService;
import com.egf.db.core.jdbc.JdbcServiceImpl;

/**
 * mysql数据库实现
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class DbMysqlImpl implements DbInterface {

	private JdbcService jdbcService = new JdbcServiceImpl();

	private final static String PRIMARY_KEY="PRIMARY KEY";
    
	public String[] getPrimaryKeyColumn(String tableName) {
		String sql="SELECT k.COLUMN_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS t LEFT JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE k USING(CONSTRAINT_NAME,TABLE_SCHEMA,TABLE_NAME) WHERE t.CONSTRAINT_TYPE=? AND t.TABLE_SCHEMA=DATABASE() AND t.TABLE_NAME=?";
		List<Object[]> list=(List<Object[]>) jdbcService.find(sql,new String[]{PRIMARY_KEY,tableName.toUpperCase()});
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
		String sql="SELECT t.CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS t LEFT JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE k USING(CONSTRAINT_NAME,TABLE_SCHEMA,TABLE_NAME) WHERE t.CONSTRAINT_TYPE=? AND t.TABLE_SCHEMA=DATABASE() AND t.TABLE_NAME=?";
		List<Object[]> list=(List<Object[]>) jdbcService.find(sql, new String[]{PRIMARY_KEY,tableName.toUpperCase()});
		if(list!=null&&!list.isEmpty()){
			return (String)list.get(0)[0];
		}
		return null;
	}

}
