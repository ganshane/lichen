package lichen.jdbc.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * jdbc util class.
 * @author  jcai
 */
class JdbcUtil {
    private static Logger logger =
        LoggerFactory.getLogger(JdbcUtil.class);

   public static void close(final Statement stmt, final ResultSet rs) {
      close(stmt);
      close(rs);
   }

   /**
     * Close the given JDBC Connection and ignore any thrown exception.
     * This is useful for typical finally blocks in manual JDBC code.
     * @param con the JDBC Connection to close (may be <code>null</code>)
     */
    public static void close(final Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                logger.warn(ex.toString());
            } catch (Throwable ex) {
                logger.warn(ex.toString());
            }
        }
    }

    /**
     * Close the given JDBC Statement and ignore any thrown exception.
     * This is useful for typical finally blocks in manual JDBC code.
     * @param stmt the JDBC Statement to close (may be <code>null</code>)
     */
    public static void close(final Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                logger.warn(ex.toString());
            } catch (Throwable ex) {
                logger.warn(ex.toString());
            }
        }
    }

    /**
     * Close the given JDBC ResultSet and ignore any thrown exception.
     * This is useful for typical finally blocks in manual JDBC code.
     * @param rs the JDBC ResultSet to close (may be <code>null</code>)
     */
    public static void close(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                logger.warn(ex.toString());
            } catch (Throwable ex) {
                logger.warn(ex.toString());
            }
        }
    }
}
