package lichen.jdbc.internal;

import lichen.core.services.LichenException;
import lichen.jdbc.services.JdbcErrorCode;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Author: Erdinc YILMAZEL
 * Date: Feb 19, 2009
 * Time: 1:49:53 PM
 */
public class ExtendedJdbcHelper extends JdbcHelper {
   public ExtendedJdbcHelper(DataSource dataSource) {
      super(dataSource);
   }

   public DatabaseMetaData getDbMetaData() {
      Connection con = null;
      try {
         con = getConnection();
         return con.getMetaData();
      } catch (SQLException e) {
          throw LichenException.wrap(e, JdbcErrorCode.DATA_ACCESS_ERROR);
      } finally {
         freeConnection(con);
      }
   }

   
}
