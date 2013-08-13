package lichen.ar.internal.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#VARCHAR} 成 {@link java.lang.String} 对象.
 * @author weiweng
 *
 */
public class VarcharType implements FieldType<String> {

    @Override
    public final String get(final ResultSet rs, final int index)
        throws SQLException {
        return rs.getString(index);
    }

    @Override
    public final void set(final PreparedStatement ps, final int index,
        final String object) throws SQLException {
        ps.setString(index, object);
    }
}
