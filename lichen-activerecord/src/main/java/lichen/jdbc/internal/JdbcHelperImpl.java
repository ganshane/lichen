package lichen.jdbc.internal;

import lichen.core.services.LichenException;
import lichen.jdbc.services.JdbcErrorCode;
import lichen.jdbc.services.JdbcHelper;
import lichen.jdbc.services.PreparedStatementSetter;
import lichen.jdbc.services.ResultSetCallback;
import lichen.jdbc.services.ResultSetGetter;
import lichen.jdbc.services.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现JdbcHelper,此类非线程安全，只能在某一个线程中运行.
 *
 * @author jcai
 */
public final class JdbcHelperImpl implements JdbcHelper {
    //操作的底层数据库
    private final DataSource _dataSource;
    //当前线程绑定的事务
    private final ThreadLocal<Transaction> currentTransaction;

    public JdbcHelperImpl(final DataSource dataSource) {
        this._dataSource = dataSource;
        currentTransaction = new ThreadLocal<Transaction>();
    }

    //事务定义
    private static class Transaction {
        private Connection _connection;
        private boolean _autoCommit;
        private int _hold;

        Transaction(Connection connection, final boolean autoCommit) {
            this._connection = connection;
            this._autoCommit = autoCommit;
        }
    }

    @Override
    public void holdConnection() {
        Transaction transaction = currentTransaction.get();

        try {
            if (transaction == null) {
                transaction = new Transaction(
                        _dataSource.getConnection(),
                        true);
            }

            transaction._hold++;

            currentTransaction.set(transaction);
        } catch (SQLException e) {
            throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
        }
    }

    @Override
    public void releaseConnection() {
        Transaction transaction = currentTransaction.get();
        if (transaction == null) {
            throw new RuntimeException("There isn't a current _connection to release");
        }

        transaction._hold--;

        if (transaction._hold == 0) {
            if (!transaction._autoCommit) {
                try {
                    transaction._connection.commit();
                    transaction._connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
                } finally {
                    JdbcUtil.close(transaction._connection);
                    currentTransaction.remove();
                }
            } else {
                JdbcUtil.close(transaction._connection);
                currentTransaction.remove();
            }
        }
    }

    @Override
    public void beginTransaction() {
        Transaction transaction = currentTransaction.get();
        try {
            if (transaction == null) {
                transaction = new Transaction(_dataSource.getConnection(), false);
            } else {
                transaction._autoCommit = false;
            }
            transaction._connection.setAutoCommit(false);

            transaction._hold++;

            currentTransaction.set(transaction);
        } catch (SQLException e) {
            throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
        }
    }

    @Override
    public void commitTransaction() {
        Transaction transaction = currentTransaction.get();

        if (transaction == null || transaction._autoCommit) {
            throw new LichenException(
                    "There isn't a current transaction to comit",
                    JdbcErrorCode.NO_TRANSACTION_IN_CURRENT_THREAD);
        } else {
            try {
                transaction._connection.commit();
                transaction._connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
            } finally {
                JdbcUtil.close(transaction._connection);
                currentTransaction.remove();
            }
        }
    }

    private Connection getConnection() throws SQLException {
        Transaction transaction = currentTransaction.get();

        if (transaction == null) {
            return _dataSource.getConnection();
        } else {
            return transaction._connection;
        }
    }

    private boolean isConnectionHeld() {
        return currentTransaction.get() != null;
    }

    private boolean isInTransaction() {
        Transaction transaction = currentTransaction.get();
        return transaction != null && !transaction._autoCommit;
    }

    @Override
    public int execute(final String sql, final Object... params) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean rollBack = false;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            int index = 0;
            for (Object obj : params) {
                ps.setObject(++index, obj);
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            rollBack = isInTransaction();
            throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
        } finally {
            JdbcUtil.close(ps);
            if (rollBack) {
                rollbackTransaction();
            } else {
                freeConnection(conn);
            }
        }
    }

    @Override
    public <T> List<T> queryForList(final String sql, final RowMapper<T> mapper) {
        return internalQueryForList(sql, mapper);
    }

    @Override
    public <T> List<T> queryForList(final String sql, final RowMapper<T> mapper, final PreparedStatementSetter... setters) {
        return internalQueryForList(sql, mapper, setters);
    }

    private <T> List<T> internalQueryForList(final String sql, final RowMapper<T> mapper, final PreparedStatementSetter... setters) {
        Connection conn = null;
        PreparedStatement ps = null;
        List<T> list = new ArrayList<T>();
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            int index = 1;
            for (PreparedStatementSetter setter : setters) {
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

    @Override
    public <T> T queryForFirst(final String sql, final ResultSetGetter<T> getter, final PreparedStatementSetter... setters) {
        return withResultSet(sql, new ResultSetCallback<T>() {
            @Override
            public T doInResultSet(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return getter.get(rs, 0);
                }
                return null;
            }
        }, setters);
    }

    private void freeConnection(final Connection con) {
        if (!isConnectionHeld()) {
            JdbcUtil.close(con);
        }
    }

    private void rollbackTransaction() {
        Transaction transaction = currentTransaction.get();

        if (transaction == null || transaction._autoCommit) {
            throw new RuntimeException("There isn't a current transaction to rollback");
        } else {
            try {
                transaction._connection.rollback();
                transaction._connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
            } finally {
                JdbcUtil.close(transaction._connection);
                currentTransaction.remove();
            }
        }
    }

    @Override
    public <T> T withResultSet(String sql, ResultSetCallback<T> callback) {
        return internalWithResultSet(sql, callback);
    }

    @Override
    public <T> T withResultSet(String sql, ResultSetCallback<T> callback,
                               PreparedStatementSetter... setters) {
        return internalWithResultSet(sql, callback, setters);
    }

    private <T> T internalWithResultSet(String sql, ResultSetCallback<T> callback,
                                        PreparedStatementSetter... setters) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            int index = 1;
            for (PreparedStatementSetter setter : setters) {
                setter.set(ps, index);
                index++;
            }
            ResultSet rs = ps.executeQuery();
            return callback.doInResultSet(rs);
        } catch (SQLException e) {
            throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
        } finally {
            JdbcUtil.close(ps);
            freeConnection(conn);
        }
    }
}
