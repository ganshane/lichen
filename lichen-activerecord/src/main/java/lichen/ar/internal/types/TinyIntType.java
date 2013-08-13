package lichen.ar.internal.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#TINYINT} 成 {@link java.lang.Byte} 对象.
 * @author weiweng
 *
 */
public class TinyIntType implements FieldType<Byte> {

    @Override
    public final Byte get(final ResultSet rs, final int index)
        throws SQLException {
        return rs.getByte(index);
    }

    @Override
    public final void set(final PreparedStatement ps, final int index,
        final Byte object) throws SQLException {
        ps.setByte(index, object);
    }
}
