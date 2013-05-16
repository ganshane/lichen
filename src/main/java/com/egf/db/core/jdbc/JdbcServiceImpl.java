/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,JdbcServiceImpl.java,fangj Exp$
 * created at:下午12:28:48
 */
package com.egf.db.core.jdbc;

import java.sql.Connection;
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

	private static final Logger logger = Logger
			.getLogger(JdbcServiceImpl.class);

	public void autoCommitExecute(String sql) {
		autoCommitExecute(sql, new Object[0]);
	}

	public void autoCommitExecute(String sql, final Object[] params) {
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
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error(e1.getStackTrace());
				e1.printStackTrace();
			}
			logger.error(e.getStackTrace());
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

	public void execute(String sql, Connection conn) {
		execute(sql, conn, new Object[0]);
	}

	public void execute(String sql, Connection conn, final Object[] params) {
		PreparedStatement pstmt = null;
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i, params[i]);
			}
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
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
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error(e1.getStackTrace());
				e1.printStackTrace();
			}
			logger.error(e.getStackTrace());
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
}
