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
final class JdbcUtil {

    private JdbcUtil() {

    }
    private static Logger logger = LoggerFactory.getLogger(JdbcUtil.class);

    public static void close(Statement stmt, ResultSet rs) {
        close(stmt);
        close(rs);
    }

   /**
     * Close the given JDBC Connection and ignore any thrown exception.
     * This is useful for typical finally blocks in manual JDBC code.
     * @param con the JDBC Connection to close (may be <code>null</code>)
     */
    public static void close(Connection con) {
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
    public static void close(Statement stmt) {
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
    public static void close(ResultSet rs) {
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
