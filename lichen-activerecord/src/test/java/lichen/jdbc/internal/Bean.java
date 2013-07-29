package lichen.jdbc.internal;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Author: Erdinc YILMAZEL
 * Date: Dec 30, 2008
 * Time: 5:00:21 PM
 */
class Bean {
   int id;
   String name;
   Timestamp creationDate;
   long big;
   BigDecimal money;
   boolean sm;
   byte[] data;

   static StatementMapper<Bean> statementMapper;

   public static StatementMapper<Bean> getStatementMapper() {
      if (statementMapper == null) {
         statementMapper = new StatementMapper<Bean>() {
            public void mapStatement(PreparedStatement stmt, Bean object) throws SQLException {
               stmt.setString(1, object.name);
               stmt.setTimestamp(2, object.creationDate);
               stmt.setLong(3, object.big);
               stmt.setBigDecimal(4, object.money);
               stmt.setBoolean(5, object.sm);
               stmt.setBytes(6, object.data);
            }
         };
      }
      return statementMapper;
   }

   static BeanCreator<Bean> beanCreator;

   public static BeanCreator<Bean> getResultSetMapper() {
      if (beanCreator == null) {
         beanCreator = new BeanCreator<Bean>() {
            public Bean createBean(ResultSet rs) throws SQLException {
               Bean bean = new Bean();
               bean.id = rs.getInt("jdbctest.id");
               bean.name = rs.getString("jdbctest.name");
               bean.creationDate = rs.getTimestamp("jdbctest.creation_date");
               return bean;
            }
         };
      }
      return beanCreator;
   }
}
