package lichen.jdbc.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lichen.core.services.LichenException;
import lichen.jdbc.services.JdbcErrorCode;
import lichen.jdbc.services.JdbcHelper;
import lichen.jdbc.services.PreparedStatementSetter;
import lichen.jdbc.services.RowMapper;

/**
 * 实现JdbcHelper,此类非线程安全，只能在某一个线程中运行
 * @author jcai
 */
public class JdbcHelperImpl implements JdbcHelper {
    //操作的底层数据库
    private final DataSource dataSource;
    //当前线程绑定的事务
    private final ThreadLocal<Transaction> currentTransaction;
    public JdbcHelperImpl(DataSource dataSource){
        this.dataSource =dataSource;
        currentTransaction = new ThreadLocal<Transaction>();
    }
    //事务定义
    private static class Transaction {
        Connection connection;
        boolean autoCommit;
        int hold;

        Transaction(Connection connection, boolean autoCommit) {
            this.connection = connection;
            this.autoCommit = autoCommit;
        }
    }
    @Override
    public void holdConnection() {
        Transaction transaction = currentTransaction.get();

        try {
            if (transaction == null) {
                transaction = new Transaction(dataSource.getConnection(), true);
            }

            transaction.hold++;

            currentTransaction.set(transaction);
        } catch (SQLException e) {
            throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
        }
    }
    @Override
    public void releaseConnection() {
        Transaction transaction = currentTransaction.get();
        if (transaction == null) {
            throw new RuntimeException("There isn't a current connection to release");
        }

        transaction.hold--;

        if (transaction.hold == 0) {
            if (!transaction.autoCommit) {
                try {
                    transaction.connection.commit();
                    transaction.connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw LichenException.wrap(e,JdbcErrorCode.DATA_ACCESS_ERROR);
                } finally {
                    JdbcUtil.close(transaction.connection);
                    currentTransaction.remove();
                }
            } else {
                JdbcUtil.close(transaction.connection);
                currentTransaction.remove();
            }
        }
    }

    @Override
    public void beginTransaction() {
        Transaction transaction = currentTransaction.get();
        try {
            if (transaction == null) {
                transaction = new Transaction(dataSource.getConnection(), false);
            } else {
                transaction.autoCommit = false;
            }
            transaction.connection.setAutoCommit(false);

            transaction.hold++;

            currentTransaction.set(transaction);
        } catch (SQLException e) {
            throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
        }
    }

    @Override
    public void commitTransaction() {
        Transaction transaction = currentTransaction.get();

        if (transaction == null || transaction.autoCommit) {
            throw new LichenException("There isn't a current transaction to comit",JdbcErrorCode.NO_TRANSACTION_IN_CURRENT_THREAD);
        } else {
            try {
                transaction.connection.commit();
                transaction.connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw LichenException.wrap(e,JdbcErrorCode.DATA_ACCESS_ERROR);
            } finally {
                JdbcUtil.close(transaction.connection);
                currentTransaction.remove();
            }
        }
    }
    private Connection getConnection() throws SQLException {
        Transaction transaction = currentTransaction.get();

        if (transaction == null) {
            return dataSource.getConnection();
        } else {
            return transaction.connection;
        }
    }
    private boolean isConnectionHeld() {
        return currentTransaction.get() != null;
    }
    private boolean isInTransaction() {
        Transaction transaction = currentTransaction.get();
        return transaction != null && !transaction.autoCommit;
    }

    @Override
    public int execute(String sql,Object ... params) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean rollBack = false;
        try{
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            int index =0;
            for(Object obj:params){
                ps.setObject(++index,obj);
            }
            return ps.executeUpdate();
        }catch(SQLException e){
            rollBack = isInTransaction();
            throw LichenException.wrap(e,JdbcErrorCode.DATA_ACCESS_ERROR);
        }finally{
            JdbcUtil.close(ps);
            if(rollBack){
                rollbackTransaction();
            }else{
                freeConnection(conn);
            }
        }
    }

    @Override
    public <T> List<T> queryForList(String sql, RowMapper<T> mapper) {
		Connection conn = null;
		PreparedStatement ps = null;
		List<T> list = new ArrayList<T>();
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			int index = 0;
			while (rs.next()) {
				list.add(mapper.mapRow(rs, index));
				index++;
			}
			return list;
		} catch (SQLException e) {
			throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
		} finally {
			JdbcUtil.close(ps);
			freeConnection(conn);
		}
    }

    @Override
	public <T> List<T> queryForList(String sql, RowMapper<T> mapper,PreparedStatementSetter... setters) {
    	Connection conn = null;
		PreparedStatement ps = null;
		List<T> list = new ArrayList<T>();
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			int index = 1;
			for(PreparedStatementSetter setter : setters) {
				setter.set(ps, index);
				index++;
			}
			ResultSet rs = ps.executeQuery();
			index = 0;
			while (rs.next()) {
				list.add(mapper.mapRow(rs, index));
				index++;
			}
			return list;
		} catch (SQLException e) {
			throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
		} finally {
			JdbcUtil.close(ps);
			freeConnection(conn);
		}
	}

    private void freeConnection(Connection con) {
        if (!isConnectionHeld()) {
            JdbcUtil.close(con);
        }
    }
    private void rollbackTransaction() {
        Transaction transaction = currentTransaction.get();

        if (transaction == null || transaction.autoCommit) {
            throw new RuntimeException("There isn't a current transaction to rollback");
        } else {
            try {
                transaction.connection.rollback();
                transaction.connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw LichenException.wrap(e,JdbcErrorCode.DATA_ACCESS_ERROR);
            } finally {
                JdbcUtil.close(transaction.connection);
                currentTransaction.remove();
            }
        }
    }
}
