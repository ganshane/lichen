package lichen.jdbc.internal;

import java.math.BigDecimal;
import java.sql.*;

/**
 * Author: Erdinc YILMAZEL
 * Date: Feb 3, 2009
 * Time: 1:31:54 AM
 */
public class ExecutableStatement {
   Connection con;
   PreparedStatement stmt;
   JdbcHelper jdbc;

   public ExecutableStatement(JdbcHelper jdbc, String sql) {
      this.jdbc = jdbc;
      try {
         con = jdbc.getConnection();
         stmt = con.prepareStatement(sql);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e, sql);
         }
         throw new JdbcException("Error running query:\n" + sql + "\n\nError: " + e.getMessage(), e);
      }
   }

   public void addBatch() {
      try {
         stmt.addBatch();
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public int execute() {
      try {
         return stmt.executeUpdate();
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public int[] executeBatch() {
      try {
         return stmt.executeBatch();
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      } finally {
         JdbcUtil.close(stmt);
         jdbc.freeConnection(con);
      }
   }

   public void setParams(Object... params) {
      try {
         jdbc.fillStatement(stmt, params);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setNull(int parameterIndex, int sqlType) {
      try {
         stmt.setNull(parameterIndex, sqlType);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setBoolean(int parameterIndex, boolean x) {
      try {
         stmt.setBoolean(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setByte(int parameterIndex, byte x) {
      try {
         stmt.setByte(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setShort(int parameterIndex, short x) {
      try {
         stmt.setShort(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setInt(int parameterIndex, int x) {
      try {
         stmt.setInt(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setLong(int parameterIndex, long x) {
      try {
         stmt.setLong(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setFloat(int parameterIndex, float x) {
      try {
         stmt.setFloat(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setDouble(int parameterIndex, double x) {
      try {
         stmt.setDouble(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setBigDecimal(int parameterIndex, BigDecimal x) {
      try {
         stmt.setBigDecimal(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setString(int parameterIndex, String x) {
      try {
         stmt.setString(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setBytes(int parameterIndex, byte x[]) {
      try {
         stmt.setBytes(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setDate(int parameterIndex, java.sql.Date x) {
      try {
         stmt.setDate(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setTime(int parameterIndex, java.sql.Time x) {
      try {
         stmt.setTime(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setTimestamp(int parameterIndex, java.sql.Timestamp x) {
      try {
         stmt.setTimestamp(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setAsciiStream(int parameterIndex, java.io.InputStream x, int length) {
      try {
         stmt.setAsciiStream(parameterIndex, x, length);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setBinaryStream(int parameterIndex, java.io.InputStream x,
                               int length) {
      try {
         stmt.setBinaryStream(parameterIndex, x, length);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   void setBlob(int parameterIndex, Blob x) {
      try {
         stmt.setBlob(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   void setClob(int parameterIndex, Clob x) {
      try {
         stmt.setClob(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   void setArray(int parameterIndex, Array x) {
      try {
         stmt.setArray(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void clearParameters() {
      try {
         stmt.clearParameters();
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setObject(int parameterIndex, Object x, int targetSqlType) {
      try {
         stmt.setObject(parameterIndex, x, targetSqlType);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

   public void setObject(int parameterIndex, Object x) {
      try {
         stmt.setObject(parameterIndex, x);
      } catch (SQLException e) {
         if (jdbc.logger != null) {
             jdbc.logger.log(e);
         }
         throw new JdbcException(e);
      }
   }

}
