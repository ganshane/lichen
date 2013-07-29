package lichen.jdbc.internal;

import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * Author: Erdinc YILMAZEL
 * Date: Dec 30, 2008
 * Time: 4:44:02 PM
 */
public interface StatementMapper<T> {
   public void mapStatement(PreparedStatement stmt, T object) throws SQLException;
}
