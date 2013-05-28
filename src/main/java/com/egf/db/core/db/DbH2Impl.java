/*		
 * Copyright 2013-5-24 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DbH2Impl.java,fangj Exp$
 * created at:上午11:34:48
 */
package com.egf.db.core.db;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.egf.db.core.jdbc.JdbcService;
import com.egf.db.core.jdbc.JdbcServiceImpl;
import com.egf.db.utils.StringUtils;

/**
 * H2数据库实现
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class DbH2Impl implements DbInterface {
	
	Logger logger=Logger.getLogger(DbH2Impl.class);
	
    private JdbcService jdbcService=new JdbcServiceImpl();
    
    private final static String PRIMARY_KEY="PRIMARY KEY";
    
	public String[] getPrimaryKeyColumn(String tableName) {
		String sql="SELECT COLUMN_LIST FROM INFORMATION_SCHEMA.CONSTRAINTS WHERE TABLE_NAME=? AND CONSTRAINT_TYPE=?";
		List<Object[]> list=(List<Object[]>) jdbcService.find(sql,new String[]{tableName.toUpperCase(),PRIMARY_KEY});
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
		List<Object[]> list=(List<Object[]>) jdbcService.find(sql.toString(), new String[]{name.toUpperCase(),PRIMARY_KEY});
		if(list!=null&&!list.isEmpty()){
			return (String)list.get(0)[0];
		}
		return null;
	}

	
	public void createSchema(String schema) throws SQLException{
		//判断用户是否存在
		String schemaSql="SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME=?";
		String name= (String) jdbcService.unique(schemaSql,new String[]{schema.toUpperCase()});
		if(StringUtils.isBlank(name)){
			String sql="CREATE SCHEMA "+schema.toUpperCase();
			logger.debug("\n"+sql);
			jdbcService.execute(sql);
		}
	}
}
