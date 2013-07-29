package lichen.jdbc.internal;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/**
 * JdbcHelper is a wrapper around JDBC API for performing common tasks such as insert, or
 * select operations.
 *
 * To create a JdbcHelper, you need a {@link javax.sql.DataSource} instance. In a J2EE
 * environment this is generally provided by your application server if you defined a
 * connection pool. The datasource instance can be looked up from the JNDI registery
 * or it may be injected to your application classes by the app server.
 *
 * Some Jdbc driver vendors also provide implementations of DataSource such as mysql's
 * MysqlDataSource and MysqlConnectionPoolDataSource.
 *
 * After creating a JdbcHelper, you can call it's methods for your db operations. JdbcHelper
 * is a threadsafe class, so there is no risk in sharing the same instance with other threads.
 *
 * If you have multiple databases within your application, you should create a seperate
 * instance of JdbcHelper per datasource.
 *
 * @author Erdinc Yilmazel
 */
public class JdbcHelper {
   ExceptionLogger logger;

   DataSource dataSource;

   /**
    * Creates a new JdbcHelper instance for the provided data source
    * @param dataSource The data source that this instance of JdbcHelper will use
    */
   public JdbcHelper(DataSource dataSource) {
      this.dataSource = dataSource;
      currentTransaction = new ThreadLocal<Transaction>();
   }

   /**
    * Sets the logger that will automatically log any SQLException that is caught
    * @param logger Logger
    */
   public void setLogger(ExceptionLogger logger) {
       this.logger = logger;
   }

   /**
    * If the supplied connection is bound to the current thread, this binding is removed
    * and the connection is closed.
    * @param con A connection that was previously retrieved from the JdbcHelper instance
    * @see #getConnection()
    */
   public void freeConnection(Connection con) {
      if (!isConnectionHeld()) {
         JdbcUtil.close(con);
      }
   }

   /**
    * If the current thread is within a transaction using this instance of JdbcHelper,
    * the connection instance for that transaction is returned. If there is no current
    * transaction, then simply returns a new connection from the datasource.
    *
    * It is the callers responsibility to close the connection. The safest way is to
    * use the {#freeConnection} method to close the connection.
    * @return Returns a connection from the data source
    * @throws SQLException May be thrown by the underlying data source
    * @see #freeConnection(java.sql.Connection)
    */
   public Connection getConnection() throws SQLException {
      Transaction transaction = currentTransaction.get();

      if (transaction == null) {
         return dataSource.getConnection();
      } else {
         return transaction.connection;
      }
   }

   abstract class QueryCallback<T> {
      public abstract T process(ResultSet rs) throws SQLException;

      public T noResult() {
         return null;
      }

      public int getFetchSize() {
         return 0;
      }

      public int getMaxRows() {
         return 0;
      }

      public int getTimeout() {
         return 0;
      }
   }

   abstract class ParameteredQueryCallback<T> extends QueryCallback<T> {
      ResultSetHandler handler;

      protected ParameteredQueryCallback(ResultSetHandler handler) {
         this.handler = handler;
      }

      @Override
      public int getFetchSize() {
         return handler.fetchSize;
      }

      @Override
      public int getMaxRows() {
         return handler.maxRows;
      }

      @Override
      public int getTimeout() {
         return handler.timeOut;
      }
   }

   private ThreadLocal<Transaction> currentTransaction;

   private static class Transaction {
      Connection connection;
      boolean autoCommit;
      int hold;

      Transaction(Connection connection, boolean autoCommit) {
         this.connection = connection;
         this.autoCommit = autoCommit;
      }
   }

   /**
    * Checks if the JdbcHelper instance is currently holding to a connection instance for the current thread.
    * If true, consecutive calls to the internal api within the same thread are all performed using the
    * same database connection.
    *
    * @return Returns true if a connection is bound to the current thread
    */
   public boolean isConnectionHeld() {
      return currentTransaction.get() != null;
   }

   /**
    * Checks if JdbcHelper instance is both holding db connection for the current thread and that connection
    * is in autocommit = false mode.
    * @return Returns true if there is a transaction for the current thread
    * @see #isConnectionHeld()
    */
   public boolean isInTransaction() {
      Transaction transaction = currentTransaction.get();
      return transaction != null && !transaction.autoCommit;
   }

   /**
    * Binds the db connection to the current thread and also sets the autocommit property of the connection
    * to false. Since the connection is hold, consecutive calls to the JdbcHelper api will be made using the
    * same database connection instance until the transaction is commited, rolled back or the connection is
    * freed.
    *
    * @see #isConnectionHeld()
    * @see #isInTransaction()
    * @see #holdConnection()
    * @see #commitTransaction()
    * @see #rollbackTransaction()
    * @see #releaseConnection()
    */
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
         if (logger != null) {
             logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   /**
    * Binds the db connection to the current thread. This makes consecutive calls to the JdbcHelper api
    * using this instance of JdbcHelper will use the same database connection until the connection is released.
    *
    * @see #isConnectionHeld()
    * @see #isInTransaction()
    * @see #holdConnection()
    * @see #commitTransaction()
    * @see #rollbackTransaction()
    * @see #releaseConnection()
    */
   public void holdConnection() {
      Transaction transaction = currentTransaction.get();

      try {
         if (transaction == null) {
            transaction = new Transaction(dataSource.getConnection(), true);
         }

         transaction.hold++;

         currentTransaction.set(transaction);
      } catch (SQLException e) {
         if (logger != null) {
             logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   /**
    * Removes the binding between the current thread and the underlying connection.
    */
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
               if (logger != null) {
                  logger.log(e);
               }
               throw new JdbcException(e);
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

   /**
    * Commits the transaction and removes the binding between the current thread and the underlying database
    * connection
    */
   public void commitTransaction() {
      Transaction transaction = currentTransaction.get();

      if (transaction == null || transaction.autoCommit) {
         throw new RuntimeException("There isn't a current transaction to comit");
      } else {
         try {
            transaction.connection.commit();
            transaction.connection.setAutoCommit(true);
         } catch (SQLException e) {
            if (logger != null) {
               logger.log(e);
            }
            throw new JdbcException(e);
         } finally {
            JdbcUtil.close(transaction.connection);
            currentTransaction.remove();
         }
      }
   }


   /**
    * Rolls back the transaction and removes the binding between the current thread and the underlying database
    * connection
    */
   public void rollbackTransaction() {
      Transaction transaction = currentTransaction.get();

      if (transaction == null || transaction.autoCommit) {
         throw new RuntimeException("There isn't a current transaction to rollback");
      } else {
         try {
            transaction.connection.rollback();
            transaction.connection.setAutoCommit(true);
         } catch (SQLException e) {
            if (logger != null) {
               logger.log(e);
            }
            throw new JdbcException(e);
         } finally {
            JdbcUtil.close(transaction.connection);
            currentTransaction.remove();
         }
      }
   }

   /**
    * This is a mysql specific method for getting the auto_increment value of the last inserted row.
    *
    * For this method to work as expected, first the connection should be hold, second the value
    * should be inserted and third this method should be called. Finally the user should release the
    * connection.
    *
    * Example:
    * <pre>
    * jdbc.holdConnection();
    * jdbc.execute("insert into t (name) values (?)", "test");
    * long id = jdbc.getLastInsertId();
    * jdbc.releaseConnection();
    * </pre>
    * @return Returns the auto_increment key for last inserted row from a mysql database server.
    */
   public long getLastInsertId() {
      Transaction transaction = currentTransaction.get();

      if (transaction == null) {
         throw new RuntimeException("There isn't a current transaction");
      } else {
         return queryForLong("SELECT last_insert_id();");
      }
   }

   protected <T> T genericQuery(String sql, QueryCallback<T> callback, Object... params) throws NoResultException {
      Connection con = null;
      Statement stmt = null;
      ResultSet result = null;
      boolean rollBack = false;

      try {
         con = getConnection();

         if (params.length == 0) {
            stmt = con.createStatement();

            if (callback.getFetchSize() != 0) {
               stmt.setFetchSize(callback.getFetchSize());
            }

            if (callback.getMaxRows() != 0) {
               stmt.setMaxRows(callback.getMaxRows());
            }

            if (callback.getTimeout() != 0) {
               stmt.setQueryTimeout(callback.getTimeout());
            }

            result = stmt.executeQuery(sql);
         } else {
            stmt = fillStatement(con.prepareStatement(sql), params);

            if (callback.getFetchSize() != 0) {
               stmt.setFetchSize(callback.getFetchSize());
            }

            if (callback.getMaxRows() != 0) {
               stmt.setMaxRows(callback.getMaxRows());
            }

            if (callback.getTimeout() != 0) {
               stmt.setQueryTimeout(callback.getTimeout());
            }
            result = ((PreparedStatement) stmt).executeQuery();
         }

         boolean n = result.next();
         if (!n) {
            throw new NoResultException();
         }

         do {
            T t = callback.process(result);
            if (t != null) {
               return t;
            }
         } while (result.next());


      } catch (SQLException e) {
         if (logger != null) {
             logger.log(e, sql);
         }

         rollBack = isInTransaction();

         throw new JdbcException("Error running query:\n" + sql + "\n\nError: " + e.getMessage(), e);
      } finally {
         JdbcUtil.close(stmt, result);
         if (rollBack)
         {
             rollbackTransaction();
         }
         else
         {
             freeConnection(con);
         }
      }
      return null;
   }

   protected <T> T genericQuery(String sql, QueryCallback<T> callback, StatementPopulator populator) throws NoResultException {
      Connection con = null;
      Statement stmt = null;
      ResultSet result = null;
      boolean rollBack = false;

      try {
         con = getConnection();

         if (populator == null) {
            stmt = con.createStatement();

            if (callback.getFetchSize() != 0) {
               stmt.setFetchSize(callback.getFetchSize());
            }

            if (callback.getMaxRows() != 0) {
               stmt.setMaxRows(callback.getMaxRows());
            }

            if (callback.getTimeout() != 0) {
               stmt.setQueryTimeout(callback.getTimeout());
            }

            result = stmt.executeQuery(sql);
         } else {
            stmt = con.prepareStatement(sql);
            populator.populateStatement((PreparedStatement) stmt);

            if (callback.getFetchSize() != 0) {
               stmt.setFetchSize(callback.getFetchSize());
            }

            if (callback.getMaxRows() != 0) {
               stmt.setMaxRows(callback.getMaxRows());
            }

            if (callback.getTimeout() != 0) {
               stmt.setQueryTimeout(callback.getTimeout());
            }
            result = ((PreparedStatement) stmt).executeQuery();
         }

         boolean n = result.next();
         if (!n) {
            throw new NoResultException();
         }

         do {
            T t = callback.process(result);
            if (t != null) {
               return t;
            }
         } while (result.next());


      } catch (SQLException e) {
         if (logger != null) {
             logger.log(e, sql);
         }

         rollBack = isInTransaction();
         throw new JdbcException("Error running query:\n" + sql + "\n\nError: " + e.getMessage(), e);
      } finally {
         JdbcUtil.close(stmt, result);
         if (rollBack)
         {
             rollbackTransaction();
         }
         else
         {
             freeConnection(con);
         }
      }
      return null;
   }

   /**
    * Performs a query on the database and creates a bean for each row from the result set,
    * then returns these beans as an ArrayList.
    * The order of the result set is preserved in the retured ArrayList object.
    *
    * If the result set is empty, an empty ArrayList is returned
    *
    * @param sql The query
    * @param beanCreator The bean creator instance for creating the java bean from the result set row
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @param <T> Type of the bean
    * @return Returns an ArrayList of objects for the provided generic type
    * @see lichen.jdbc.internal.BeanCreator
    */
   public <T> ArrayList<T> queryForList(String sql, final BeanCreator<T> beanCreator, Object... params) {
      final ArrayList<T> list = new ArrayList<T>();
      try {
         genericQuery(sql, new QueryCallback<T>() {
            public T process(ResultSet rs) throws SQLException {
               T t = beanCreator.createBean(rs);
               if(t instanceof JdbcAware) {
                  ((JdbcAware) t).setJdbcHelper(JdbcHelper.this);
               }
               list.add(t);
               return null;
            }
         }, params);
      } catch (NoResultException e) {
         //
      }
      return list;
   }


   public <T> ArrayList<T> queryForList(String sql, final BeanCreator<T> beanCreator, StatementPopulator populator) {
      final ArrayList<T> list = new ArrayList<T>();
      try {
         genericQuery(sql, new QueryCallback<T>() {
            public T process(ResultSet rs) throws SQLException {
               T t = beanCreator.createBean(rs);
               if(t instanceof JdbcAware) {
                  ((JdbcAware) t).setJdbcHelper(JdbcHelper.this);
               }
               list.add(t);
               return null;
            }
         }, populator);
      } catch (NoResultException e) {
         //
      }
      return list;
   }

   /**
    * If only one integer column is selected in the query, you can use this method to get a list of
    * all these integers from the result set as an ArrayList<Integer>
    *
    * <p>Example:</p>
    * <pre>
    * ArrayList<Integer> list = jdbc.queryForIntegerList("select userid from users where register_date < '2009'");
    * </pre>
    *
    * If more than one column is selected, only the first column is used and the others are discarded.
    *
    * If the result set is empty, an empty ArrayList is returned
    *
    * @param sql An sql query, where the first column that is selected is an integer.
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns an ArrayList of Integer objects from the result set
    */
   public ArrayList<Integer> queryForIntegerList(String sql, Object... params) {
      final ArrayList<Integer> list = new ArrayList<Integer>();
      try {
         genericQuery(sql, new QueryCallback<Integer>() {
            public Integer process(ResultSet rs) throws SQLException {
               Integer t = rs.getInt(1);
               list.add(t);
               return null;
            }
         }, params);
      } catch (NoResultException e) {
         //
      }
      return list;
   }

   public ArrayList<Integer> queryForIntegerList(String sql, StatementPopulator populator) {
      final ArrayList<Integer> list = new ArrayList<Integer>();
      try {
         genericQuery(sql, new QueryCallback<Integer>() {
            public Integer process(ResultSet rs) throws SQLException {
               Integer t = rs.getInt(1);
               list.add(t);
               return null;
            }
         }, populator);
      } catch (NoResultException e) {
         //
      }
      return list;
   }

   /**
    * If only one String column is selected in the query, you can use this method to get a list of
    * all these strings from the result set as an ArrayList<String>
    *
    * <p>Example:</p>
    * <pre>
    * ArrayList<String> list = jdbc.queryForStringList("select username from users where register_date < '2009'");
    * </pre>
    *
    * If more than one column is selected, only the first column is used and the others are discarded.
    *
    * If the result set is empty, an empty ArrayList is returned
    *
    * @param sql An sql query, where the first column that is selected is a string.
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns an ArrayList of String objects from the result set
    */
   public ArrayList<String> queryForStringList(String sql, Object... params) {
      final ArrayList<String> list = new ArrayList<String>();
      try {
         genericQuery(sql, new QueryCallback<String>() {
            public String process(ResultSet rs) throws SQLException {
               String t = rs.getString(1);
               list.add(t);
               return null;
            }
         }, params);
      } catch (NoResultException e) {
         //
      }
      return list;
   }

   public ArrayList<String> queryForStringList(String sql, StatementPopulator populator) {
      final ArrayList<String> list = new ArrayList<String>();
      try {
         genericQuery(sql, new QueryCallback<String>() {
            public String process(ResultSet rs) throws SQLException {
               String t = rs.getString(1);
               list.add(t);
               return null;
            }
         }, populator);
      } catch (NoResultException e) {
         //
      }
      return list;
   }

   /**
    * This method is similar to {@link #queryForList(String, BeanCreator, Object...)}, but
    * may be used to create more than one bean per row. A seperate BeanCreator for each
    * bean type has to be provided for this method.
    *
    * If the result set is empty, an empty ArrayList is returned
    *
    * @param sql The query
    * @param xCreator Bean creator for the fist bean
    * @param yCreator Bean creator for the second bean
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @param <X> The type of first bean
    * @param <Y> The type of second bean
    * @return Returns an ArrayList of created beans from the result set
    * @see lichen.jdbc.internal.BeanCreator
    */
   public <X, Y> ArrayList<Tuple<X, Y>> queryForList(String sql,
                                                     final BeanCreator<X> xCreator,
                                                     final BeanCreator<Y> yCreator,
                                                     Object... params) {
      final ArrayList<Tuple<X, Y>> list = new ArrayList<Tuple<X, Y>>();
      try {
         genericQuery(sql, new QueryCallback<Tuple<X, Y>>() {
            public Tuple<X, Y> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               list.add(new Tuple<X, Y>(x, y));
               return null;
            }
         }, params);
      } catch (NoResultException e) {
         //
      }
      return list;
   }

   public <X, Y> ArrayList<Tuple<X, Y>> queryForList(String sql,
                                                     final BeanCreator<X> xCreator,
                                                     final BeanCreator<Y> yCreator,
                                                     final StatementPopulator populator) {
      final ArrayList<Tuple<X, Y>> list = new ArrayList<Tuple<X, Y>>();
      try {
         genericQuery(sql, new QueryCallback<Tuple<X, Y>>() {
            public Tuple<X, Y> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               list.add(new Tuple<X, Y>(x, y));
               return null;
            }
         }, populator);
      } catch (NoResultException e) {
         //
      }
      return list;
   }

   /**
    * This method is similar to {@link #queryForList(String, BeanCreator, Object...)}, but
    * may be used to create more than one bean per row. A seperate BeanCreator for each
    * bean type has to be provided for this method.
    *
    * If the result set is empty, an empty ArrayList is returned
    *
    * @param sql The query
    * @param xCreator Bean creator for the fist bean
    * @param yCreator Bean creator for the second bean
    * @param zCreator Bean creator for the third bean
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @param <X> The type of first bean
    * @param <Y> The type of second bean
    * @param <Z> The type of third bean
    * @return Returns an ArrayList of created beans from the result set
    * @see lichen.jdbc.internal.BeanCreator
    */
   public <X, Y, Z> ArrayList<Triple<X, Y, Z>> queryForList(String sql,
                                                     final BeanCreator<X> xCreator,
                                                     final BeanCreator<Y> yCreator,
                                                     final BeanCreator<Z> zCreator,
                                                     Object... params) {
      final ArrayList<Triple<X, Y, Z>> list = new ArrayList<Triple<X, Y, Z>>();
      try {
         genericQuery(sql, new QueryCallback<Triple<X, Y, Z>>() {
            public Triple<X, Y, Z> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               Z z = zCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               if(z instanceof JdbcAware) {
                  ((JdbcAware) z).setJdbcHelper(JdbcHelper.this);
               }
               list.add(new Triple<X, Y, Z>(x, y, z));
               return null;
            }
         }, params);
      } catch (NoResultException e) {
         //
      }
      return list;
   }

   public <X, Y, Z> ArrayList<Triple<X, Y, Z>> queryForList(String sql,
                                                     final BeanCreator<X> xCreator,
                                                     final BeanCreator<Y> yCreator,
                                                     final BeanCreator<Z> zCreator,
                                                     final StatementPopulator populator) {
      final ArrayList<Triple<X, Y, Z>> list = new ArrayList<Triple<X, Y, Z>>();
      try {
         genericQuery(sql, new QueryCallback<Triple<X, Y, Z>>() {
            public Triple<X, Y, Z> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               Z z = zCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               if(z instanceof JdbcAware) {
                  ((JdbcAware) z).setJdbcHelper(JdbcHelper.this);
               }
               list.add(new Triple<X, Y, Z>(x, y, z));
               return null;
            }
         }, populator);
      } catch (NoResultException e) {
         //
      }
      return list;
   }


   /**
    * This method is similar to {@link #queryForList(String, BeanCreator, Object...)}, but
    * may be used to create more than one bean per row. A seperate BeanCreator for each
    * bean type has to be provided for this method.
    *
    * If the result set is empty, an empty ArrayList is returned
    *
    * @param sql The query
    * @param xCreator Bean creator for the fist bean
    * @param yCreator Bean creator for the second bean
    * @param zCreator Bean creator for the third bean
    * @param wCreator Bean creator for the forth bean
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @param <X> The type of first bean
    * @param <Y> The type of second bean
    * @param <Z> The type of third bean
    * @param <W> The type of forth bean
    * @return Returns an ArrayList of created beans from the result set
    * @see lichen.jdbc.internal.BeanCreator
    */
   public <X, Y, Z, W> ArrayList<Quadruple<X, Y, Z, W>> queryForList(String sql,
                                                     final BeanCreator<X> xCreator,
                                                     final BeanCreator<Y> yCreator,
                                                     final BeanCreator<Z> zCreator,
                                                     final BeanCreator<W> wCreator,
                                                     Object... params) {
      final ArrayList<Quadruple<X, Y, Z, W>> list = new ArrayList<Quadruple<X, Y, Z, W>>();
      try {
         genericQuery(sql, new QueryCallback<Quadruple<X, Y, Z, W>>() {
            public Quadruple<X, Y, Z, W> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               Z z = zCreator.createBean(rs);
               W w = wCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               if(z instanceof JdbcAware) {
                  ((JdbcAware) z).setJdbcHelper(JdbcHelper.this);
               }
               if(w instanceof JdbcAware) {
                  ((JdbcAware) w).setJdbcHelper(JdbcHelper.this);
               }
               list.add(new Quadruple<X, Y, Z, W>(x, y, z, w));
               return null;
            }
         }, params);
      } catch (NoResultException e) {
         //
      }
      return list;
   }

   public <X, Y, Z, W> ArrayList<Quadruple<X, Y, Z, W>> queryForList(String sql,
                                                     final BeanCreator<X> xCreator,
                                                     final BeanCreator<Y> yCreator,
                                                     final BeanCreator<Z> zCreator,
                                                     final BeanCreator<W> wCreator,
                                                     final StatementPopulator populator) {
      final ArrayList<Quadruple<X, Y, Z, W>> list = new ArrayList<Quadruple<X, Y, Z, W>>();
      try {
         genericQuery(sql, new QueryCallback<Quadruple<X, Y, Z, W>>() {
            public Quadruple<X, Y, Z, W> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               Z z = zCreator.createBean(rs);
               W w = wCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               if(z instanceof JdbcAware) {
                  ((JdbcAware) z).setJdbcHelper(JdbcHelper.this);
               }
               if(w instanceof JdbcAware) {
                  ((JdbcAware) w).setJdbcHelper(JdbcHelper.this);
               }
               list.add(new Quadruple<X, Y, Z, W>(x, y, z, w));
               return null;
            }
         }, populator);
      } catch (NoResultException e) {
         //
      }
      return list;
   }


   /**
    * This method is similar to {@link #queryForList(String, BeanCreator, Object...)}, but
    * may be used to create more than one bean per row. A seperate BeanCreator for each
    * bean type has to be provided for this method.
    *
    * If the result set is empty, an empty ArrayList is returned
    *
    * @param sql The query
    * @param xCreator Bean creator for the fist bean
    * @param yCreator Bean creator for the second bean
    * @param zCreator Bean creator for the third bean
    * @param wCreator Bean creator for the forth bean
    * @param qCreator Bean creator for the fifth bean
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @param <X> The type of first bean
    * @param <Y> The type of second bean
    * @param <Z> The type of third bean
    * @param <W> The type of forth bean
    * @param <Q> The type of fifth bean
    * @return Returns an ArrayList of created beans from the result set
    * @see lichen.jdbc.internal.BeanCreator
    */
   public <X, Y, Z, W, Q> ArrayList<Pentuple<X, Y, Z, W, Q>> queryForList(String sql,
                                                     final BeanCreator<X> xCreator,
                                                     final BeanCreator<Y> yCreator,
                                                     final BeanCreator<Z> zCreator,
                                                     final BeanCreator<W> wCreator,
                                                     final BeanCreator<Q> qCreator,
                                                     Object... params) {
      final ArrayList<Pentuple<X, Y, Z, W, Q>> list = new ArrayList<Pentuple<X, Y, Z, W, Q>>();
      try {
         genericQuery(sql, new QueryCallback<Pentuple<X, Y, Z, W, Q>>() {
            public Pentuple<X, Y, Z, W, Q> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               Z z = zCreator.createBean(rs);
               W w = wCreator.createBean(rs);
               Q q = qCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               if(z instanceof JdbcAware) {
                  ((JdbcAware) z).setJdbcHelper(JdbcHelper.this);
               }
               if(w instanceof JdbcAware) {
                  ((JdbcAware) w).setJdbcHelper(JdbcHelper.this);
               }
               if(q instanceof JdbcAware) {
                  ((JdbcAware) q).setJdbcHelper(JdbcHelper.this);
               }
               list.add(new Pentuple<X, Y, Z, W, Q>(x, y, z, w, q));
               return null;
            }
         }, params);
      } catch (NoResultException e) {
         //
      }
      return list;
   }

   public <X, Y, Z, W, Q> ArrayList<Pentuple<X, Y, Z, W, Q>> queryForList(String sql,
                                                     final BeanCreator<X> xCreator,
                                                     final BeanCreator<Y> yCreator,
                                                     final BeanCreator<Z> zCreator,
                                                     final BeanCreator<W> wCreator,
                                                     final BeanCreator<Q> qCreator,
                                                     final StatementPopulator populator) {
      final ArrayList<Pentuple<X, Y, Z, W, Q>> list = new ArrayList<Pentuple<X, Y, Z, W, Q>>();
      try {
         genericQuery(sql, new QueryCallback<Pentuple<X, Y, Z, W, Q>>() {
            public Pentuple<X, Y, Z, W, Q> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               Z z = zCreator.createBean(rs);
               W w = wCreator.createBean(rs);
               Q q = qCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               if(z instanceof JdbcAware) {
                  ((JdbcAware) z).setJdbcHelper(JdbcHelper.this);
               }
               if(w instanceof JdbcAware) {
                  ((JdbcAware) w).setJdbcHelper(JdbcHelper.this);
               }
               if(q instanceof JdbcAware) {
                  ((JdbcAware) q).setJdbcHelper(JdbcHelper.this);
               }
               list.add(new Pentuple<X, Y, Z, W, Q>(x, y, z, w, q));
               return null;
            }
         }, populator);
      } catch (NoResultException e) {
         //
      }
      return list;
   }

   /**
    * If two columns are selected in a query, you can retrieve the result set as a map of
    * key => value pairs using this method.
    *
    * If the query does not return any rows, an empty SortedMap is returned.
    *
    * @param sql The sql query that selects at least two columns.
    * @param resultSetMapper A ResultSetMapper to create map entries from the result set rows
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @param <K> Type of key items
    * @param <V> Type of value items
    * @return Returns a SortedMap of entries
    */
   public <K, V> SortedMap<K, V> queryForMap(String sql, final ResultSetMapper<K, V> resultSetMapper, Object... params) {
      final SortedMap<K, V> map = new TreeMap<K, V>();
      try {
         genericQuery(sql, new QueryCallback<AbstractMap.SimpleEntry<K, V>>() {
            @Override
            public AbstractMap.SimpleEntry<K, V> process(ResultSet rs) throws SQLException {
               AbstractMap.SimpleEntry<K, V> entry = resultSetMapper.mapRow(rs);
               map.put(entry.getKey(), entry.getValue());
               return null;
            }
         }, params);
      } catch (NoResultException e) {
         //
      }

      return map;
   }

   public <K, V> SortedMap<K, V> queryForMap(String sql, final ResultSetMapper<K, V> resultSetMapper, final StatementPopulator populator) {
      final SortedMap<K, V> map = new TreeMap<K, V>();
      try {
         genericQuery(sql, new QueryCallback<AbstractMap.SimpleEntry<K, V>>() {
            @Override
            public AbstractMap.SimpleEntry<K, V> process(ResultSet rs) throws SQLException {
               AbstractMap.SimpleEntry<K, V> entry = resultSetMapper.mapRow(rs);
               map.put(entry.getKey(), entry.getValue());
               return null;
            }
         }, populator);
      } catch (NoResultException e) {
         //
      }

      return map;
   }

   /**
    * Creates an object for a single row in the result set and returns it. The object is created using the
    * supplied BeanCreator.
    *
    * If the result set is empty, null is returned.
    * If the result set contains more than one row, only the first row is read.
    * 
    * @param sql The sql query
    * @param beanCreator The BeanCreator instance that will be used to create the returned object
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @param <T> The type of object that will be returned
    * @return The created bean from the result set row
    */
   public <T> T queryForObject(String sql, final BeanCreator<T> beanCreator, Object... params) {
      try {
         return genericQuery(sql, new QueryCallback<T>() {
            public T process(ResultSet rs) throws SQLException {
               T t = beanCreator.createBean(rs);
               if(t instanceof JdbcAware) {
                  ((JdbcAware) t).setJdbcHelper(JdbcHelper.this);
               }
               return t;
            }
         }, params);
      } catch (NoResultException e) {
         return null;
      }
   }

   public <T> T queryForObject(String sql, final BeanCreator<T> beanCreator, final StatementPopulator populator) {
      try {
         return genericQuery(sql, new QueryCallback<T>() {
            public T process(ResultSet rs) throws SQLException {
               T t = beanCreator.createBean(rs);
               if(t instanceof JdbcAware) {
                  ((JdbcAware) t).setJdbcHelper(JdbcHelper.this);
               }
               return t;
            }
         }, populator);
      } catch (NoResultException e) {
         return null;
      }
   }

   public <X, Y> Tuple<X, Y> queryForTuple(String sql,
                                           final BeanCreator<X> xCreator,
                                           final BeanCreator<Y> yCreator,
                                           Object... params) {
      try {
         return genericQuery(sql, new QueryCallback<Tuple<X, Y>>() {
            public Tuple<X, Y> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               return new Tuple<X, Y>(x, y);
            }
         }, params);
      } catch (NoResultException e) {
         return null;
      }
   }

   public <X, Y> Tuple<X, Y> queryForTuple(String sql,
                                           final BeanCreator<X> xCreator,
                                           final BeanCreator<Y> yCreator,
                                           final StatementPopulator populator) {
      try {
         return genericQuery(sql, new QueryCallback<Tuple<X, Y>>() {
            public Tuple<X, Y> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               return new Tuple<X, Y>(x, y);
            }
         }, populator);
      } catch (NoResultException e) {
         return null;
      }
   }

   public <X, Y, Z> Triple<X, Y, Z> queryForTriple(String sql,
                                           final BeanCreator<X> xCreator,
                                           final BeanCreator<Y> yCreator,
                                           final BeanCreator<Z> zCreator,
                                           Object... params) {
      try {
         return genericQuery(sql, new QueryCallback<Triple<X, Y, Z>>() {
            public Triple<X, Y, Z> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               Z z = zCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               if(z instanceof JdbcAware) {
                  ((JdbcAware) z).setJdbcHelper(JdbcHelper.this);
               }

               return new Triple<X, Y, Z>(x, y, z);
            }
         }, params);
      } catch (NoResultException e) {
         return null;
      }
   }

   public <X, Y, Z> Triple<X, Y, Z> queryForTriple(String sql,
                                           final BeanCreator<X> xCreator,
                                           final BeanCreator<Y> yCreator,
                                           final BeanCreator<Z> zCreator,
                                           final StatementPopulator populator) {
      try {
         return genericQuery(sql, new QueryCallback<Triple<X, Y, Z>>() {
            public Triple<X, Y, Z> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               Z z = zCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               if(z instanceof JdbcAware) {
                  ((JdbcAware) z).setJdbcHelper(JdbcHelper.this);
               }

               return new Triple<X, Y, Z>(x, y, z);
            }
         }, populator);
      } catch (NoResultException e) {
         return null;
      }
   }

   public <X, Y, Z, W> Quadruple<X, Y, Z, W> queryForQuadruple(String sql,
                                           final BeanCreator<X> xCreator,
                                           final BeanCreator<Y> yCreator,
                                           final BeanCreator<Z> zCreator,
                                           final BeanCreator<W> wCreator,
                                           Object... params) {
      try {
         return genericQuery(sql, new QueryCallback<Quadruple<X, Y, Z, W>>() {
            public Quadruple<X, Y, Z, W> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               Z z = zCreator.createBean(rs);
               W w = wCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               if(z instanceof JdbcAware) {
                  ((JdbcAware) z).setJdbcHelper(JdbcHelper.this);
               }
               if(w instanceof JdbcAware) {
                  ((JdbcAware) w).setJdbcHelper(JdbcHelper.this);
               }

               return new Quadruple<X, Y, Z, W>(x, y, z, w);
            }
         }, params);
      } catch (NoResultException e) {
         return null;
      }
   }

   public <X, Y, Z, W> Quadruple<X, Y, Z, W> queryForQuadruple(String sql,
                                           final BeanCreator<X> xCreator,
                                           final BeanCreator<Y> yCreator,
                                           final BeanCreator<Z> zCreator,
                                           final BeanCreator<W> wCreator,
                                           final StatementPopulator populator) {
      try {
         return genericQuery(sql, new QueryCallback<Quadruple<X, Y, Z, W>>() {
            public Quadruple<X, Y, Z, W> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               Z z = zCreator.createBean(rs);
               W w = wCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               if(z instanceof JdbcAware) {
                  ((JdbcAware) z).setJdbcHelper(JdbcHelper.this);
               }
               if(w instanceof JdbcAware) {
                  ((JdbcAware) w).setJdbcHelper(JdbcHelper.this);
               }

               return new Quadruple<X, Y, Z, W>(x, y, z, w);
            }
         }, populator);
      } catch (NoResultException e) {
         return null;
      }
   }

   public <X, Y, Z, W, Q> Pentuple<X, Y, Z, W, Q> queryForPentuple(String sql,
                                           final BeanCreator<X> xCreator,
                                           final BeanCreator<Y> yCreator,
                                           final BeanCreator<Z> zCreator,
                                           final BeanCreator<W> wCreator,
                                           final BeanCreator<Q> qCreator,
                                           Object... params) {
      try {
         return genericQuery(sql, new QueryCallback<Pentuple<X, Y, Z, W, Q>>() {
            public Pentuple<X, Y, Z, W, Q> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               Z z = zCreator.createBean(rs);
               W w = wCreator.createBean(rs);
               Q q = qCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               if(z instanceof JdbcAware) {
                  ((JdbcAware) z).setJdbcHelper(JdbcHelper.this);
               }
               if(w instanceof JdbcAware) {
                  ((JdbcAware) w).setJdbcHelper(JdbcHelper.this);
               }
               if(q instanceof JdbcAware) {
                  ((JdbcAware) q).setJdbcHelper(JdbcHelper.this);
               }

               return new Pentuple<X, Y, Z, W, Q>(x, y, z, w, q);
            }
         }, params);
      } catch (NoResultException e) {
         return null;
      }
   }

   public <X, Y, Z, W, Q> Pentuple<X, Y, Z, W, Q> queryForPentuple(String sql,
                                           final BeanCreator<X> xCreator,
                                           final BeanCreator<Y> yCreator,
                                           final BeanCreator<Z> zCreator,
                                           final BeanCreator<W> wCreator,
                                           final BeanCreator<Q> qCreator,
                                           final StatementPopulator populator) {
      try {
         return genericQuery(sql, new QueryCallback<Pentuple<X, Y, Z, W, Q>>() {
            public Pentuple<X, Y, Z, W, Q> process(ResultSet rs) throws SQLException {
               X x = xCreator.createBean(rs);
               Y y = yCreator.createBean(rs);
               Z z = zCreator.createBean(rs);
               W w = wCreator.createBean(rs);
               Q q = qCreator.createBean(rs);
               if(x instanceof JdbcAware) {
                  ((JdbcAware) x).setJdbcHelper(JdbcHelper.this);
               }
               if(y instanceof JdbcAware) {
                  ((JdbcAware) y).setJdbcHelper(JdbcHelper.this);
               }
               if(z instanceof JdbcAware) {
                  ((JdbcAware) z).setJdbcHelper(JdbcHelper.this);
               }
               if(w instanceof JdbcAware) {
                  ((JdbcAware) w).setJdbcHelper(JdbcHelper.this);
               }
               if(q instanceof JdbcAware) {
                  ((JdbcAware) q).setJdbcHelper(JdbcHelper.this);
               }

               return new Pentuple<X, Y, Z, W, Q>(x, y, z, w, q);
            }
         }, populator);
      } catch (NoResultException e) {
         return null;
      }
   }

   /**
    * To select a single integer value from the database this method can be used.
    *
    * <p>Example:</p>
    * <pre>
    * int userId = jdbc.queryForInt("select user_id from users where user_name = ?", "test");
    * </pre>
    *
    * <p>Only the first column of the first row from the result set is returned as int. Other
    * columns or rows are discarded</p>
    * <p>If an empty result set is returned from the database, a NoResultException is thrown</p>
    *
    * @param sql The query sql selecting a single integer field
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns the selected int value
    * @throws NoResultException Thrown if the result set is empty
    */
   public int queryForInt(String sql, Object... params) throws NoResultException {
      return genericQuery(sql, new QueryCallback<Integer>() {
         public Integer process(ResultSet rs) throws SQLException {
            return rs.getInt(1);
         }
      }, params);
   }


   public int queryForInt(String sql, final StatementPopulator populator) throws NoResultException {
      return genericQuery(sql, new QueryCallback<Integer>() {
         public Integer process(ResultSet rs) throws SQLException {
            return rs.getInt(1);
         }
      }, populator);
   }


   /**
    * To select a single string value from the database this method can be used.
    *
    * <p>Example:</p>
    * <pre>
    * String userName = jdbc.queryForString("select user_name from users where user_id = ?", 10);
    * </pre>
    *
    * <p>Only the first column of the first row from the result set is returned as string. Other
    * columns or rows are discarded</p>
    * <p>If an empty result set is returned from the database, a NoResultException is thrown</p>
    *
    * @param sql The query sql selecting a single string field
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns the selected string value
    * @throws NoResultException Thrown if the result set is empty
    */
   public String queryForString(String sql, Object... params) throws NoResultException {
      return genericQuery(sql, new QueryCallback<String>() {
         public String process(ResultSet rs) throws SQLException {
            return rs.getString(1);
         }
      }, params);
   }

   public String queryForString(String sql, final StatementPopulator populator) throws NoResultException {
      return genericQuery(sql, new QueryCallback<String>() {
         public String process(ResultSet rs) throws SQLException {
            return rs.getString(1);
         }
      }, populator);
   }


   /**
    * To select a single long value from the database this method can be used.
    *
    * <p>Example:</p>
    * <pre>
    * long userId = jdbc.queryForLong("select user_id from users where user_name = ?", "test");
    * </pre>
    *
    * <p>Only the first column of the first row from the result set is returned as long. Other
    * columns or rows are discarded</p>
    * <p>If an empty result set is returned from the database, a NoResultException is thrown</p>
    *
    * @param sql The query sql selecting a single long field (Typically a bigint type)
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns the selected long value
    * @throws NoResultException Thrown if the result set is empty
    */
   public long queryForLong(String sql, Object... params) throws NoResultException {
      return genericQuery(sql, new QueryCallback<Long>() {
         public Long process(ResultSet rs) throws SQLException {
            return rs.getLong(1);
         }
      }, params);
   }

   public long queryForLong(String sql, final StatementPopulator populator) throws NoResultException {
      return genericQuery(sql, new QueryCallback<Long>() {
         public Long process(ResultSet rs) throws SQLException {
            return rs.getLong(1);
         }
      }, populator);
   }


   /**
    * To select a single double value from the database this method can be used.
    *
    * <p>Example:</p>
    * <pre>
    * double points = jdbc.queryForDouble("select score from users where user_name = ?", "test");
    * </pre>
    *
    * <p>Only the first column of the first row from the result set is returned as double. Other
    * columns or rows are discarded</p>
    * <p>If an empty result set is returned from the database, a NoResultException is thrown</p>
    *
    * @param sql The query sql selecting a single double field
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns the selected double value
    * @throws NoResultException Thrown if the result set is empty
    */
   public double queryForDouble(String sql, Object... params) throws NoResultException {
      return genericQuery(sql, new QueryCallback<Double>() {
         public Double process(ResultSet rs) throws SQLException {
            return rs.getDouble(1);
         }
      }, params);
   }

   public double queryForDouble(String sql, final StatementPopulator populator) throws NoResultException {
      return genericQuery(sql, new QueryCallback<Double>() {
         public Double process(ResultSet rs) throws SQLException {
            return rs.getDouble(1);
         }
      }, populator);
   }


   /**
    * To select a single float value from the database this method can be used.
    *
    * <p>Example:</p>
    * <pre>
    * float points = jdbc.queryForFloat("select score from users where user_name = ?", "test");
    * </pre>
    *
    * <p>Only the first column of the first row from the result set is returned as float. Other
    * columns or rows are discarded</p>
    * <p>If an empty result set is returned from the database, a NoResultException is thrown</p>
    *
    * @param sql The query sql selecting a single float field
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns the selected float value
    * @throws NoResultException Thrown if the result set is empty
    */
   public float queryForFloat(String sql, Object... params) throws NoResultException {
      return genericQuery(sql, new QueryCallback<Float>() {
         public Float process(ResultSet rs) throws SQLException {
            return rs.getFloat(1);
         }
      }, params);
   }

   public float queryForFloat(String sql, final StatementPopulator populator) throws NoResultException {
      return genericQuery(sql, new QueryCallback<Float>() {
         public Float process(ResultSet rs) throws SQLException {
            return rs.getFloat(1);
         }
      }, populator);
   }

   /**
    * To select a single timestamp value from the database this method can be used.
    *
    * <p>Example:</p>
    * <pre>
    * Timestamp date = jdbc.queryForTimestamp("select registration_date from users where user_name = ?", "test");
    * </pre>
    *
    * <p>Only the first column of the first row from the result set is returned as timestamp. Other
    * columns or rows are discarded</p>
    * <p>If an empty result set is returned from the database, a NoResultException is thrown</p>
    *
    * @param sql The query sql selecting a single timestamp field
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns the selected timestamp value
    * @throws NoResultException Thrown if the result set is empty
    */
   public Timestamp queryForTimestamp(String sql, Object... params) throws NoResultException {
      return genericQuery(sql, new QueryCallback<Timestamp>() {
         public Timestamp process(ResultSet rs) throws SQLException {
            return rs.getTimestamp(1);
         }
      }, params);
   }

   public Timestamp queryForTimestamp(String sql, final StatementPopulator populator) throws NoResultException {
      return genericQuery(sql, new QueryCallback<Timestamp>() {
         public Timestamp process(ResultSet rs) throws SQLException {
            return rs.getTimestamp(1);
         }
      }, populator);
   }


   /**
    * To select a single BigDecimal value from the database this method can be used.
    *
    * <p>Only the first column of the first row from the result set is returned as BigDecimal. Other
    * columns or rows are discarded</p>
    * <p>If an empty result set is returned from the database, a NoResultException is thrown</p>
    *
    * @param sql The query sql selecting a single BigDecimal field
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns the selected BigDecimal value
    * @throws NoResultException Thrown if the result set is empty
    */
   public BigDecimal queryForBigDecimal(String sql, Object... params) throws NoResultException {
      return genericQuery(sql, new QueryCallback<BigDecimal>() {
         public BigDecimal process(ResultSet rs) throws SQLException {
            return rs.getBigDecimal(1);
         }
      }, params);
   }

   public BigDecimal queryForBigDecimal(String sql, final StatementPopulator populator) throws NoResultException {
      return genericQuery(sql, new QueryCallback<BigDecimal>() {
         public BigDecimal process(ResultSet rs) throws SQLException {
            return rs.getBigDecimal(1);
         }
      }, populator);
   }


   /**
    * To select a single byte[] value from the database this method can be used.
    *
    * <p>Only the first column of the first row from the result set is returned as byte[]. Other
    * columns or rows are discarded</p>
    * <p>If an empty result set is returned from the database, a NoResultException is thrown</p>
    *
    * @param sql The query sql selecting a single byte[] field
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns the selected byte[] value
    * @throws NoResultException Thrown if the result set is empty
    */
   public byte[] queryForBytes(String sql, Object... params) throws NoResultException {
      return genericQuery(sql, new QueryCallback<byte[]>() {
         public byte[] process(ResultSet rs) throws SQLException {
            return rs.getBytes(1);
         }
      }, params);
   }

   public byte[] queryForBytes(String sql, final StatementPopulator populator) throws NoResultException {
      return genericQuery(sql, new QueryCallback<byte[]>() {
         public byte[] process(ResultSet rs) throws SQLException {
            return rs.getBytes(1);
         }
      }, populator);
   }

   /**
    * To select a single boolean value from the database this method can be used.
    *
    * <p>Example:</p>
    * <pre>
    * boolean active = jdbc.queryForBoolea ("select active from users where user_name = ?", "test");
    * </pre>
    *
    * <p>Only the first column of the first row from the result set is returned as boolean. Other
    * columns or rows are discarded</p>
    * <p>If an empty result set is returned from the database, a NoResultException is thrown</p>
    *
    * @param sql The query sql selecting a single boolean field (Typically an unsigned tinyint)
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns the selected boolean value
    * @throws NoResultException Thrown if the result set is empty
    */
   public boolean queryForBoolean(String sql, Object... params) throws NoResultException {
      return genericQuery(sql, new QueryCallback<Boolean>() {
         public Boolean process(ResultSet rs) throws SQLException {
            return rs.getBoolean(1);
         }
      }, params);
   }

   public boolean queryForBoolean(String sql, final StatementPopulator populator) throws NoResultException {
      return genericQuery(sql, new QueryCallback<Boolean>() {
         public Boolean process(ResultSet rs) throws SQLException {
            return rs.getBoolean(1);
         }
      }, populator);
   }

   /**
    * To select a single value from the database as an Ascii Stream this method can be used.
    *
    * <p>Only the first column of the first row from the result set is returned as InputStream. Other
    * columns or rows are discarded</p>
    * <p>If an empty result set is returned from the database, a NoResultException is thrown</p>
    *
    * @param sql The query sql selecting a single Ascii Stream field
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns the selected Ascii Stream value
    * @throws NoResultException Thrown if the result set is empty
    */
   public InputStream queryForAsciiStream(String sql, Object... params) throws NoResultException {
      return genericQuery(sql, new QueryCallback<InputStream>() {
         public InputStream process(ResultSet rs) throws SQLException {
            return rs.getAsciiStream(1);
         }
      }, params);
   }

   public InputStream queryForAsciiStream(String sql, final StatementPopulator populator) throws NoResultException {
      return genericQuery(sql, new QueryCallback<InputStream>() {
         public InputStream process(ResultSet rs) throws SQLException {
            return rs.getAsciiStream(1);
         }
      }, populator);
   }

   /**
    * To select a single value from the database as an Binary Stream this method can be used.
    *
    * <p>Only the first column of the first row from the result set is returned as InputStream. Other
    * columns or rows are discarded</p>
    * <p>If an empty result set is returned from the database, a NoResultException is thrown</p>
    *
    * @param sql The query sql selecting a single Binary Stream field
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns the selected Binary Stream value
    * @throws NoResultException Thrown if the result set is empty
    */
   public InputStream queryForBinaryStream(String sql, Object... params) throws NoResultException {
      return genericQuery(sql, new QueryCallback<InputStream>() {
         public InputStream process(ResultSet rs) throws SQLException {
            return rs.getBinaryStream(1);
         }
      }, params);
   }

   public InputStream queryForBinaryStream(String sql, final StatementPopulator populator) throws NoResultException {
      return genericQuery(sql, new QueryCallback<InputStream>() {
         public InputStream process(ResultSet rs) throws SQLException {
            return rs.getBinaryStream(1);
         }
      }, populator);
   }

   /**
    * To select a single value from the database as an Character Stream (Reader) this method can be used.
    *
    * <p>Only the first column of the first row from the result set is returned as Reader. Other
    * columns or rows are discarded</p>
    * <p>If an empty result set is returned from the database, a NoResultException is thrown</p>
    *
    * @param sql The query sql selecting a single Character Stream field
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns the selected Character Stream value
    * @throws NoResultException Thrown if the result set is empty
    */
   public Reader queryForCharacterStream(String sql, Object... params) throws NoResultException {
      return genericQuery(sql, new QueryCallback<Reader>() {
         public Reader process(ResultSet rs) throws SQLException {
            return rs.getCharacterStream(1);
         }
      }, params);
   }

   public Reader queryForCharacterStream(String sql, final StatementPopulator populator) throws NoResultException {
      return genericQuery(sql, new QueryCallback<Reader>() {
         public Reader process(ResultSet rs) throws SQLException {
            return rs.getCharacterStream(1);
         }
      }, populator);
   }


   /**
    * Performs the given query on the database and uses the given ResultSetHandler to process the resultset.
    * This method is handy for running a query with a set of inline resultset processing instructions passed
    * as an anonymous class.
    *
    * <p>Example:</p>
    * <pre>
    * jdbc.query("select * from users where userId < ?", new ResultSetHandler() {
    *    public void processRow(ResultSet rs) throws SQLException {
    *       // this method is called for every row in the resultset.
    *       // you should implement your business logic here, but never alter the
    *       // passed ResultSet object.
    *    }
    * }, 2000);
    * </pre>
    *
    * <p>As shown in the example above, the processRow method of the provided resultset object is called for
    * each row in the result set. The user should not call the next() method on the provided resultset unless
    * she knows what she is doing.</p>
    *
    * <p>Sometimes iterating over a large resultset may cause OutOfMemory errors. To avoid this, a constructor
    * of {@link ResultSetHandler} with a fetchSize parameter should be used.</p>
    *
    * <p>If something goes wrong with the query, a JdbcException may be thrown. This exception is a
    * RuntimeException, so it doesn't have to be explicitly catched</p>
    *
    * @param sql The query to be executed
    * @param handler The ResultSetHandler that will be used in iterating over the resultset
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns true if the query is successful and a non-empty resultset was returned
    */
   @SuppressWarnings("unchecked")
   public boolean query(String sql, final ResultSetHandler handler, Object... params) {
      try {
         genericQuery(sql, new ParameteredQueryCallback(handler) {
            public Object process(ResultSet rs) throws SQLException {
               this.handler.rowNo++;
               this.handler.processRow(rs);
               return null;
            }
         }, params);
         return true;
      } catch (NoResultException e) {
         return false;
      }
   }

   @SuppressWarnings("unchecked")
   public boolean query(String sql, final ResultSetHandler handler, final StatementPopulator populator) {
      try {
         genericQuery(sql, new ParameteredQueryCallback(handler) {
            public Object process(ResultSet rs) throws SQLException {
               this.handler.rowNo++;
               this.handler.processRow(rs);
               return null;
            }
         }, populator);
         return true;
      } catch (NoResultException e) {
         return false;
      }
   }

   /**
    * Sometimes you want total control of the resultset handling but keep things simple and not deal with the
    * Jdbc API directly. This method may come handy in those cases. This method returns a QueryResult object
    * which is a wrapper around jdbc ResultSet class.
    *
    * <p>Example:</p>
    * <pre>
    * QueryResult result = jdbc.query("select * from users where user_id < ?", 2000);
    * result.setFetchSize(100);
    * // The query isn't actually executed until the QueryResult objects next() method is called.
    * while(result.next()) {
    *    // For each row
    *    String userName = result.getString("user_name");
    *    int userId = result.getInt("user_id");
    * }
    * result.close(); // You have to explicitly close the resultset with calling the close method
    * </pre>
    *
    * <p>
    * The {@link QueryResult} class has the same api with the {@link java.sql.ResultSet} interface.</p>
    *
    * @param sql The query sql to be executed
    * @param params Optional query parameteres that will be used as prepared statement parameters
    * @return Returns a QueryResult object which may be used to iterate over the resultset
    */
   public QueryResult query(String sql, Object... params) {
      return new QueryResult(this, sql, params);
   }

   /**
    * This method sets the query parameters of a PreparedStatement object from a given array of parameters.
    *
    * @param stmt The prepared statement that the parameters will be set on
    * @param params Array of query parameters
    * @return Returns the same prepared statement that was passed
    * @throws SQLException May be thrown by the underlying Jdbc driver
    */
   protected PreparedStatement fillStatement(PreparedStatement stmt, Object[] params) throws SQLException {
      for (int i = 0; i < params.length; i++) {
         if (params[i] != null) {
            stmt.setObject(i + 1, params[i]);
         } else {
            stmt.setNull(i + 1, Types.VARCHAR);
         }
      }

      return stmt;
   }

   /**
    * <p>This method may be used in executing statements like insert/update/delete on the server.</p>
    *
    * <p>Example:</p>
    * <pre>
    * int updatedUsers = jdbc.execute("update users set active = 0 where register_date < ?", "2009-01-01");
    * </pre>
    * <p>If something goes wrong with the execution of the statement, a JdbcException may be thrown</p>
    *
    * @param sql The sql statement to be executed on the server
    * @param params Optional parameteres that will be used as prepared statement parameters
    * @return Returns the number of affected rows
    */
   public int execute(String sql, Object... params) {
      Connection con = null;
      Statement stmt = null;
      boolean rollBack = false;

      try {
         con = getConnection();

         if (params.length == 0) {
            stmt = con.createStatement();
            return stmt.executeUpdate(sql);
         } else {
            stmt = fillStatement(con.prepareStatement(sql), params);
            return ((PreparedStatement) stmt).executeUpdate();
         }
      } catch (SQLException e) {
         if (logger != null) {
             logger.log(e, sql);
         }

         rollBack = isInTransaction();

         throw new JdbcException("Error executing query:\n" + sql + "\n\nError: " + e.getMessage(), e);
      } finally {
         JdbcUtil.close(stmt);
         if (rollBack)
         {
             rollbackTransaction();
         }
         else
         {
             freeConnection(con);
         }
      }
   }

   /**
    * <p>If you want to explicitly set the catalog name for an sql statement to be run on the server you can use
    * this method. In mysql database server for instance, you can set a default catalog name in your jdbc
    * connection url, however execute a statement on a different catalog using the same connection.</p>
    *
    * <p>The same behavior may be used by giving the catalog name in the sql statement, however if binary logging
    * is ON on the server, mysql logs this statement to be run on the default catalog unless the catalog name
    * is explicitly set. This may be a problem if your mysql slave servers are replicating some catalogs and
    * ignoring others</p>
    *
    * <p>Example:</p>
    * <pre>
    * int rowCount = jdbc.executeOnCatalog("profiles", "insert into users (user_id, user_name) values (?, ?)", 100, "test");
    * </pre>
    *
    * @param cataLogName The catalog (schema) name on the server
    * @param sql The sql statement
    * @param params Optional parameteres that will be used as prepared statement parameters
    * @return Returns the number of affected rows
    */
   public int executeOnCatalog(String cataLogName, String sql, Object... params) {
      Connection con = null;
      Statement stmt = null;
      boolean rollBack = false;

      try {
         con = getConnection();
         String currentCatalog = con.getCatalog();
         con.setCatalog(cataLogName);

         if (params.length == 0) {
            stmt = con.createStatement();
            int result = stmt.executeUpdate(sql);
            con.setCatalog(currentCatalog);
            return result;
         } else {
            stmt = fillStatement(con.prepareStatement(sql), params);
            int result = ((PreparedStatement) stmt).executeUpdate();
            con.setCatalog(currentCatalog);
            return result;
         }
      } catch (SQLException e) {
         if (logger != null) {
             logger.log(e, sql);
         }

         rollBack = isInTransaction();
         throw new JdbcException("Error executing query:\n" + sql + "\n\nError: " + e.getMessage(), e);
      } finally {
         JdbcUtil.close(stmt);
         if (rollBack)
         {
             rollbackTransaction();
         }
         else
         {
             freeConnection(con);
         }
      }
   }

   /**
    * When inserting or updating a record for a java bean, instead of giving a separate statement parameter
    * for every bean property may be cumbersome. You may write a statement mapper for that bean type and use that
    * StatementMapper everytime you execute a similar statement.
    *
    * <p>Example:</p>
    * <pre>
    * class User {
    *    String userName;
    *    int userId;
    *
    *    public static StatementMapper<User> getMapper() {
    *       return new StatementMapper<User>() {
    *          public void mapStatement(PreparedStatement stmt, User user) throws SQLException {
    *             stmt.setInt(1, user.getUserId());
    *             stmt.setString(2, user.getUserName());
    *          }
    *       }
    *    }
    * }
    *
    * User u = new User();
    * u.userName = "erdinc";
    * u.userId = 234;
    *
    * int rowCount = jdbc.execute("insert into users (user_id, user_name) values (?, ?)", u, User.getMapper());
    *
    * </pre>
    *
    * @param sql The sql statement
    * @param bean The bean to be mapped against the sql statement
    * @param mapper BeanMapper to be used to map the sql statement to the bean
    * @param <T> The Bean type
    * @return Affected row count
    */
   public <T> int execute(String sql, T bean, StatementMapper<T> mapper) {
      Connection con = null;
      PreparedStatement stmt = null;
      boolean rollBack = false;

      try {
         con = getConnection();
         stmt = con.prepareStatement(sql);
         mapper.mapStatement(stmt, bean);
         return stmt.executeUpdate();
      } catch (SQLException e) {
         if (logger != null) {
             logger.log(e, sql);
         }

         rollBack = isInTransaction();
         throw new JdbcException("Error executing query:\n" + sql + "\n\nError: " + e.getMessage(), e);
      } finally {
         JdbcUtil.close(stmt);

         if (rollBack)
         {
             rollbackTransaction();
         }
         else
         {
             freeConnection(con);
         }
      }
   }

    /**
     * This method can be used if you want explicit control on the arguments that you set on a
     * prepared statement instead of using var args.
     * @param sql The sql statement
     * @param populator The object that will populate the prepared statement parameters
     * @return Returns the number of affected rows
     */
   public int execute(String sql, StatementPopulator populator) {
      Connection con = null;
      PreparedStatement stmt = null;
      boolean rollBack = false;

      try {
         con = getConnection();
         stmt = con.prepareStatement(sql);
         populator.populateStatement(stmt);
         return stmt.executeUpdate();
      } catch (SQLException e) {
         if (logger != null) {
             logger.log(e, sql);
         }
         rollBack = isInTransaction();
         throw new JdbcException("Error executing query:\n" + sql + "\n\nError: " + e.getMessage(), e);
      } finally {
         JdbcUtil.close(stmt);
         if (rollBack)
         {
             rollbackTransaction();
         }
         else
         {
             freeConnection(con);
         }
      }
   }

   /**
    * This method can be used to execute a statement on an explicitly defined catalog name using a statement mapper.
    *
    * @see #executeOnCatalog(String, String, Object, StatementMapper)
    * @see #execute(String, Object, StatementMapper)
    * @param cataLogName Catalog (Schema) name
    * @param sql The sql statement
    * @param bean The bean to be mapped against the sql statement
    * @param mapper BeanMapper to be used to map the sql statement to the bean
    * @param <T> The Bean type
    * @return Affected row count
    */
   public <T> int executeOnCatalog(String cataLogName, String sql, T bean, StatementMapper<T> mapper) {
      Connection con = null;
      PreparedStatement stmt = null;
      boolean rollBack = false;

      try {
         con = getConnection();
         String currentCatalog = con.getCatalog();
         con.setCatalog(cataLogName);
         stmt = con.prepareStatement(sql);
         mapper.mapStatement(stmt, bean);
         int result = stmt.executeUpdate();
         con.setCatalog(currentCatalog);
         return result;
      } catch (SQLException e) {
         if (logger != null) {
             logger.log(e, sql);
         }

         rollBack = isInTransaction();
         throw new JdbcException("Error executing query:\n" + sql + "\n\nError: " + e.getMessage(), e);
      } finally {
         JdbcUtil.close(stmt);
         if (rollBack)
         {
             rollbackTransaction();
         }
         else
         {
             freeConnection(con);
         }
      }
   }

   /**
    * This is the same with {@link #execute(String, Object...)} method that doesn't return anything
    * @param sql The sql statement
    * @param params Optional parameters that will be used as prepared statement parameters
    */
   public void run(String sql, Object... params) {
      Connection con = null;
      Statement stmt = null;
      boolean rollBack = false;

      try {
         con = getConnection();

         if (params.length == 0) {
            stmt = con.createStatement();
            stmt.execute(sql);
         } else {
            stmt = fillStatement(con.prepareStatement(sql), params);
            ((PreparedStatement) stmt).execute();
         }
      } catch (SQLException e) {
         if (logger != null) {
             logger.log(e, sql);
         }
         rollBack = isInTransaction();
         throw new JdbcException("Error executing query:\n" + sql + "\n\nError: " + e.getMessage(), e);
      } finally {
         JdbcUtil.close(stmt);
         if (rollBack)
         {
             rollbackTransaction();
         }
         else
         {
             freeConnection(con);
         }
      }
   }

   /**
    * This method returns a wrapper against the jdbc PreparedStatement that may be used to execute statements
    * on the database.
    *
    * @param sql Sql statement
    * @return Returns a PreparedStatement wrapper object
    */
   public ExecutableStatement prepareStatement(String sql) {
      return new ExecutableStatement(this, sql);
   }

   /**
    * <p>To run a batch of sql statements on the server, this method may be used. A BatchFeeder object
    * is an object that has an iterator like interface which will be used to add batch statements to the underlying
    * PreparedStatement.</p>
    * <p>Example:</p>
    * <pre>
    * List<User> users = getUsers();
    *
    * // Inserts all the User objects in the list using a batch statement
    * int[] result = jdbc.executeBatch("insert into users (user_id, user_name) values (?, ?)",
    *                                  new MappingBatchFeeder<User>(users.iterator(), User.getMapper()));
    *
    * </pre>
    *
    * <p>In the example code above, a special implementation of BatchFeeder is used which iterates over the list of
    * users and accepts a statement mapper as the second constructor parameter. You can see the
    * {@link #execute(String, Object, StatementMapper)} method documentation for creating a statement mapper</p>
    *
    *
    * @param sql The sql statement to be executed
    * @param feeder BatchFeeder to be used in adding batch jobs.
    * @return Returns the affected row count for every statement that was executed as an int array
    */
   public int[] executeBatch(String sql, BatchFeeder feeder) {
      Connection con = null;
      PreparedStatement stmt = null;
      boolean rollBack = false;

      try {
         con = getConnection();

         stmt = con.prepareStatement(sql);
         while (feeder.hasNext()) {
            stmt.clearParameters();
            if(feeder.feedStatement(stmt)) {
               stmt.addBatch();
            }
         }
         return stmt.executeBatch();
      } catch (SQLException e) {
         if (logger != null) {
             logger.log(e, sql);
         }
         rollBack = isInTransaction();
         throw new JdbcException("Error executing query:\n" + sql + "\n\nError: " + e.getMessage(), e);
      } finally {
         JdbcUtil.close(stmt);
         if (rollBack)
         {
             rollbackTransaction();
         }
         else
         {
             freeConnection(con);
         }
      }
   }
}
