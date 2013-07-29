package lichen.jdbc.internal;

import java.sql.SQLException;

/**
 * Author: Erdinc Yilmazel
 * Since: 10/27/11
 */
public interface ExceptionLogger {
    public void log(SQLException ex);

    public void log(SQLException ex, String sql);
}
