/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,JdbcServiceImpl.java,fangj Exp$
 * created at:下午12:28:48
 */
package com.egf.db.core.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author fangj
 * @version $Revision: $
 * @since 1.0
 */
public class JdbcServiceImpl implements JdbcService {

	private static final Logger logger = Logger.getLogger(JdbcServiceImpl.class);

	public void execute(String sql)throws SQLException{
		execute(sql, new Object[0]);
	}

	public void execute(String sql, final Object[] params) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i, params[i]);
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
			throw new SQLException();
		} finally {
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
				pstmt.setObject(i, params[i]);
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
		} finally {
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
		return null;
	}
	
	public String[] getTablePK(String tableName) {
		String[] resultArray = null;   
		Connection conn = null;   
	    DatabaseMetaData dbmd = null;   
	    ResultSet rs = null;   
	    try {   
	        conn=DBConnectionManager.getConnection();   
	        dbmd=conn.getMetaData();   
	        rs=dbmd.getPrimaryKeys(null, null, tableName);   
	        String tempPK = "";   
	        while (rs.next())  {   
	           tempPK = rs.getString("COLUMN_NAME") + ",";   
	        }   
	        resultArray = tempPK.split(",");   
	        if (tempPK.length() < 1) {   
	          resultArray = null;   
	        }   
	    } catch(SQLException sqle) {   
	       logger.error(sqle);   
	    } finally {   
	       if (rs != null) {   
	          try{   
	            rs.close();   
	          } catch(SQLException sqle) {   
	            logger.error(sqle);   
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
	    return resultArray; 
	}
}
