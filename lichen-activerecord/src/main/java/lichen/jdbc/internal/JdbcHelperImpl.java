// Copyright 2013 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
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
public class JdbcHelperImpl implements JdbcHelper {
    //操作的底层数据库
    private DataSource _dataSource;
    //当前线程绑定的事务
    private ThreadLocal<Transaction> _currentTransaction;

    public JdbcHelperImpl(DataSource dataSource) {
        this._dataSource = dataSource;
        _currentTransaction = new ThreadLocal<Transaction>();
    }

    //事务定义
    private static class Transaction {
        private Connection _connection;
        private boolean _autoCommit;
        private int _hold;

        Transaction(Connection connection, boolean autoCommit) {
            this._connection = connection;
            this._autoCommit = autoCommit;
        }
    }

    @Override
    public void holdConnection() {
        Transaction transaction = _currentTransaction.get();

        try {
            if (transaction == null) {
                transaction = new Transaction(
                        _dataSource.getConnection(),
                        true);
            }

            transaction._hold++;

            _currentTransaction.set(transaction);
        } catch (SQLException e) {
            throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
        }
    }

    @Override
    public void releaseConnection() {
        Transaction transaction = _currentTransaction.get();
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
                    _currentTransaction.remove();
                }
            } else {
                JdbcUtil.close(transaction._connection);
                _currentTransaction.remove();
            }
        }
    }

    @Override
    public void beginTransaction() {
        Transaction transaction = _currentTransaction.get();
        try {
            if (transaction == null) {
                transaction = new Transaction(_dataSource.getConnection(), false);
            } else {
                transaction._autoCommit = false;
            }
            transaction._connection.setAutoCommit(false);

            transaction._hold++;

            _currentTransaction.set(transaction);
        } catch (SQLException e) {
            throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
        }
    }

    @Override
    public void commitTransaction() {
        Transaction transaction = _currentTransaction.get();

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
                _currentTransaction.remove();
            }
        }
    }

    private Connection getConnection() throws SQLException {
        Transaction transaction = _currentTransaction.get();

        if (transaction == null) {
            return _dataSource.getConnection();
        } else {
            return transaction._connection;
        }
    }

    private boolean isConnectionHeld() {
        return _currentTransaction.get() != null;
    }

    private boolean isInTransaction() {
        Transaction transaction = _currentTransaction.get();
        return transaction != null && !transaction._autoCommit;
    }

    @Override
    public int execute(String sql, Object... params) {
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
    public <T> List<T> queryForList(String sql, final RowMapper<T> mapper, PreparedStatementSetter... setters) {
    	return withResultSet(sql, new ResultSetCallback<List<T>>() {
            @Override
            public List<T> doInResultSet(ResultSet rs) throws SQLException {
            	int index = 0;
            	List<T> list = new ArrayList<T>();
                if (rs.next()) {
                	list.add(mapper.mapRow(rs, index));
                    index++;
                }
                return list;
            }
        }, setters);
    }

    @Override
    public <T> T queryForFirst(String sql, final ResultSetGetter<T> getter, PreparedStatementSetter... setters) {
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

    private void freeConnection(Connection con) {
        if (!isConnectionHeld()) {
            JdbcUtil.close(con);
        }
    }

    private void rollbackTransaction() {
        Transaction transaction = _currentTransaction.get();

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
                _currentTransaction.remove();
            }
        }
    }

    @Override
    public <T> T withResultSet(String sql, ResultSetCallback<T> callback,
                                        PreparedStatementSetter... setters) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            int index = 1;
            for (PreparedStatementSetter setter : setters) {
                setter.set(ps, index);
                index++;
            }
            rs = ps.executeQuery();
            return callback.doInResultSet(rs);
        } catch (SQLException e) {
            throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(ps);

            freeConnection(conn);
        }
    }
}
