package lichen.ar.internal.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#FLOAT} 成 {@link java.lang.Float} 对象.
 * @author weiweng
 *
 */
public class FloatType implements FieldType<Float> {

    @Override
    public final Float get(final ResultSet rs, final int index)
        throws SQLException {
        return rs.getFloat(index);
    }

    @Override
    public final void set(final PreparedStatement ps, final int index,
        final Float object) throws SQLException {
        ps.setFloat(index, object);
    }
}
