/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,JdbcServiceImpl.java,fangj Exp$
 * created at:下午12:28:48
 */
package lichen.migration.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import lichen.migration.core.DbConstant;
import lichen.migration.exception.MigrationException;
import lichen.migration.utils.StringUtils;

/**
 * @author fangj
 * @version $Revision: $
 * @since 1.0
 */
public class JdbcServiceImpl implements JdbcService {

	private static final Logger logger = Logger.getLogger(JdbcServiceImpl.class);

	private Connection conn = null;
	
	private PreparedStatement pstmt = null;
	
	public void execute(String sql)throws MigrationException{
		//批量执行
		String[] splitSql=sql.replaceAll("\n", "").split(";");
		for (String s : splitSql) {
			if(!StringUtils.isBlank(s)){
				execute(s, new Object[0]);
			}
		}
	}

	public void execute(String sql, final Object[] params) throws MigrationException{
		try {
			conn = DBConnectionManager.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i+1, params[i]);
			}
			pstmt.execute();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				e.printStackTrace();
			} catch (SQLException e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
			}
			throw MigrationException.newFromErrorCode(DbConstant.Migration_RETURN_CODE_01);
		} finally {
			close();
		}
	}

	public List<Object[]> find(String sql) {
		return find(sql, new Object[0]);
	}

	public List<Object[]> find(String sql, Object[] params) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			conn = DBConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i+1, params[i]);
			}
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int cc = rsmd.getColumnCount();
			while (rs.next()) {
				Object[] objs = new Object[cc];
				for (int j = 1; j <= cc; j++) {
					objs[j - 1] = rs.getObject(j);
				}
				list.add(objs);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	
	public String getColumnTypeName(String tableName, String columnName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnectionManager.getConnection();
			pstmt = conn.prepareStatement("select "+columnName+" from "+tableName+" where 1=2");
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int cc = rsmd.getColumnCount();
			for(int i = 0; i <cc; i++) {  
				  if(rsmd.getColumnName(i + 1).equals(columnName.toUpperCase())){
					  return rsmd.getColumnTypeName(i + 1);
				  }
			    }  

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	public Object unique(String sql) {
		return unique(sql, new Object[0]);
	}

	
	public Object unique(String sql, Object[] params) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i+1, params[i]);
			}
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int cc = rsmd.getColumnCount();
			int i=0;
			while (rs.next()) {
				if(i==0){
					if(cc==1){
						return rs.getObject(1);
					}else{
						Object[] objs = new Object[cc];
						for (int j = 1; j <= cc; j++) {
							objs[j - 1] = rs.getObject(j);
						}
						return objs;
					}
				}
				i++;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}
	
	private  void close(){
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}