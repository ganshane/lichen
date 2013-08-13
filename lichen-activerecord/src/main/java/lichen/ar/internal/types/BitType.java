package lichen.ar.internal.types;

import lichen.ar.services.FieldType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 映射数据库中的{@link java.sql.Types#BIT} 成 {@link java.lang.Boolean} 对象.
 * @author weiweng
 */
public class BitType implements FieldType<Boolean> {

    @Override
    public final Boolean get(final ResultSet rs, final int index)
        throws SQLException {
        return rs.getBoolean(index);
    }

    @Override
    public final void set(final PreparedStatement ps, final int index,
            final Boolean object)
            throws SQLException {
        ps.setBoolean(index, object);
    }
}
