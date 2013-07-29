package lichen.jdbc.internal.ext;

import lichen.jdbc.internal.BeanCreator;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: erdinc
 * Date: Jul 23, 2009
 * Time: 1:00:55 PM
 */
public class TimestampBeanCreator implements BeanCreator<Timestamp> {
   private int index;

   public TimestampBeanCreator(int index) {
      this.index = index;
   }

   public TimestampBeanCreator() {
      index = 1;
   }

   public Timestamp createBean(ResultSet rs) throws SQLException {
      return rs.getTimestamp(index);
   }
}
