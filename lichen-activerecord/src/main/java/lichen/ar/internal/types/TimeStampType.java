package lichen.ar.internal.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#TIMESTAMP} 成 {@link java.util.Date} 对象.
 * @author weiweng
 *
 */
public class TimeStampType implements FieldType<Date> {

    @Override
    public final Date get(final ResultSet rs, final int index)
        throws SQLException {
        return rs.getTimestamp(index);
    }

    @Override
    public final void set(final PreparedStatement ps, final int index,
        final Date object) throws SQLException {
        ps.setTimestamp(index, new Timestamp(object.getTime()));
    }
}
