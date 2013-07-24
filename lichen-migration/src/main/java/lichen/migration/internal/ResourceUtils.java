// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.internal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.jar.JarFile;

/**
 * Utility object that contains functions that ensure a resource is
 * released once it has been used.  Each function takes resource
 * object that has a method to release the resource, such as close(),
 * and a closure to that operates on the resource.  After the closure
 * has completed, either normally via a return or by throwing an
 * exception, the resource is released.
 */
final class ResourceUtils{
  private final static Logger logger = LoggerFactory.getLogger(ResourceUtils.class);

  /**
   * Given a resource and two functions, the first, a closer function
   * that closes or releases the resource, and the second, a body
   * function that uses the resource, invoke the body function on the
   * resource and then ensure that the closer function closes the
   * resource, regardless if the body function returns normally or
   * throws an exception.
   *
   * @param resource a resource to use and then close
   * @param closerDescription a textual description of what the closer
   *        does; used to log any exception thrown by closer when the
   *        body also throws an exception since in that case the
   *        closer's exception will be suppressed and not thrown to
   *        the caller
   * @param closer the function that closes the resource
   * @param body the function that uses the resource
   * @return the result of invoking body on the resource
   * @throws RuntimeException any exception that invoking body on the resource throws
   */
  static <A,B> B resource(A resource, String closerDescription,Function1<A,Void> closer,Function1<A,B> body){
    Throwable primaryException = null;
    try {
      return body.apply(resource);
    }
    catch(Throwable e) {
        primaryException = e;
        throw new RuntimeException(e);
    }
    finally {
      if (primaryException != null) {
          try {
              closer.apply(resource);
          } catch (Throwable throwable) {
              logger.warn("Fail to Close when "+closerDescription,throwable);
          }
      }
      else {
        try {
          closer.apply(resource);
        }
        catch (Throwable e){
            logger.warn("Suppressing exception when " +
              closerDescription +
              ':',
              e);
        }
      }
    }
  }

  /**
   * Take a SQL connection, pass it to a closure and ensure that the
   * connection is closed after the closure returns, either normally
   * or by an exception.  If the closure returns normally, return its
   * result.
   *
   * @param connection a SQL connection
   * @param f a Function1[C <: Connection,R] that operates on the
   *        connection
   * @return the result of f
   */
  static <C extends Connection,R> R autoClosingConnection(C connection,Function1<C,R>f){
    return resource(connection, "closing connection",new Function1<C,Void>() {
        public Void apply(C parameter) {
            try {
                parameter.close();
            } catch (SQLException e) {
                new RuntimeException(e);
            }
            return null;
        }
    },f);
  }

  /**
   * Take a SQL connection, save its current auto-commit mode, put the
   * connection into the requested auto-commit mode, pass the
   * connection to a closure and ensure that the connection's
   * auto-commit mode is restored after the closure returns, either
   * normally or by an exception.  If the closure returns normally,
   * return its result.
   *
   * The connection's auto-commit mode is always set, even if it is
   * the same as the requested mode.  This is done to ensure any work
   * the database would normally do when setting the auto-commit mode
   * is always done.
   *
   * @param connection a SQL connection
   * @param mode the auto-commit mode the connection's state should be
   *        put in
   * @param f a Function1[C <: Connection,R] that operates on the
   *        connection
   * @return the result of f
   */
  static <C extends Connection,R> R autoRestoringConnection(final C connection, final boolean mode, final Function1<C,R> f){
      final boolean currentMode;
      try {
          currentMode = connection.getAutoCommit();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      return resource(connection, "restoring connection auto-commit",new Function1<C, Void>() {
          public Void apply(C parameter) throws Throwable{
              parameter.setAutoCommit(currentMode);
              return null;
          }
      },new Function1<C, R>() {
              public R apply(C parameter) throws Throwable{
                  parameter.setAutoCommit(mode);
                  return f.apply(parameter);
              }
      });
  }

    static enum CommitBehavior{
        AutoCommit,CommitUponReturnOrException,CommitUponReturnOrRollbackUponException
    }
  /**
   * Take a SQL connection, pass it to a closure and ensure that any
   * work done on the connection after the closure returns is either
   * left alone, committed or rolled back depending upon the given
   * setting.  If the closure returns normally, return its result.
   * The connection's auto-commit mode will be set and restored.
   *
   * @param connection a SQL connection
   * @param commitBehavior the operation to implement on the
   *        connection after f returns normally or via throwing an
   *        exception
   * @param f a Function1[C <: Connection,R] that operates on the
   *        connection
   * @return the result of f
   */
  static <C extends Connection,R> R autoCommittingConnection(C connection, final CommitBehavior commitBehavior, final Function1<C,R> f){
    boolean newCommitBehavior = false;
    switch (commitBehavior) {
        case AutoCommit :
            newCommitBehavior = true;
            break;
        case CommitUponReturnOrException :
            newCommitBehavior = false;
            break;
        case CommitUponReturnOrRollbackUponException :
            newCommitBehavior = false;
            break;
      }

      return autoRestoringConnection(connection, newCommitBehavior, new Function1<C, R>() {
          public R apply(C parameter) throws Throwable {
              switch (commitBehavior) {
                  case AutoCommit :
                      return f.apply(parameter);
                  case CommitUponReturnOrException :
                      return resource(parameter,"committing transaction",new Function1<C, Void>() {
                          public Void apply(C parameter) throws Throwable {
                              parameter.commit();
                              return null;
                          }
                      },f);
                  case CommitUponReturnOrRollbackUponException :
                      R result;
                      try {
                          result = f.apply(parameter);
                          parameter.commit();
                      }
                      catch (Throwable e1){
                          try {
                              parameter.rollback();
                          }
                          catch(Throwable e2) {
                              logger.warn("Suppressing exception when rolling back" +
                                      "transaction:", e2);
                          }
                          throw e1;
                      }
                      return result;
              }
              throw new IllegalStateException("Wrong Commit Type");
          }
      });
  }

  /**
   * Take a SQL statement, pass it to a closure and ensure that the
   * statement is closed after the closure returns, either normally or
   * by an exception.  If the closure returns normally, return its
   * result.
   *
   * @param statement a SQL statement
   * @param f a Function1[S <: Statement,R] that operates on the
   *        statement
   * @return the result of f
   */
  static <S extends Statement,R> R autoClosingStatement(S statement,Function1<S,R> f){
      return resource(statement, "closing statement",new Function1<S, Void>() {
          public Void apply(S parameter) throws Throwable {
              parameter.close();
              return null;
          }
      },f);
  }

  /**
   * Take a SQL result set, pass it to a closure and ensure that the
   * result set is closed after the closure returns, either normally
   * or by an exception.  If the closure returns normally, return its
   * result.
   *
   * @param resultSet a SQL result set
   * @param f a Function1[RS <: ResultSet,R] that operates on the
   *        result set
   * @return the result of f
   */
  static <RS extends ResultSet,R> R autoClosingResultSet(RS resultSet,Function1<RS,R> f){
    return resource(resultSet, "closing result set",new Function1<RS, Void>() {
        public Void apply(RS parameter) throws Throwable {
            parameter.close();
            return null;
        }
    },f);
  }

  /**
   * Take a jar file, pass it to a closure and ensure that the jar
   * file is closed after the closure returns, either normally or by
   * an exception.  If the closure returns normally, return its
   * result.
   *
   * @param jarFile a jar file
   * @param f a Function1[J <: JarFile,R] that operates on the jar
   *        file
   * @return the result of f
   */
  static <J extends JarFile,R> R jarFile(J jarFile,Function1<J,R> f){
      return resource(jarFile, "closing jar file",new Function1<J, Void>() {
          public Void apply(J parameter) throws Throwable {
              parameter.close();
              return null;
          }
      },f);
  }
}
