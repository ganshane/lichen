package lichen.ar.internal.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#INTEGER} 成 {@link java.lang.Integer} 对象.
 * @author weiweng
 *
 */
public class IntegerType implements FieldType<Integer> {

    @Override
    public final Integer get(final ResultSet rs, final int index)
        throws SQLException {
        return rs.getInt(index);
    }

    @Override
    public final void set(final PreparedStatement ps, final int index,
        final Integer object) throws SQLException {
        ps.setInt(index, object);
    }
}
