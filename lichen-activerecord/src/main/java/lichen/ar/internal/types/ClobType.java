package lichen.ar.internal.types;

import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#CLOB} 成 {@link java.sql.Clob} 对象.
 * @author weiweng
 *
 */
public class ClobType implements FieldType<Clob> {

    @Override
    public final Clob get(final ResultSet rs, final int index)
        throws SQLException {
        return rs.getClob(index);
    }

    @Override
    public final void set(final PreparedStatement ps, final int index,
        final Clob object) throws SQLException {
        ps.setClob(index, object);
    }
}
