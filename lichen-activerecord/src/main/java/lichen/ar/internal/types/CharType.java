package lichen.ar.internal.types;

import java.io.CharArrayReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#CHAR} 成 {@link java.lang.char} 对象.
 * @author weiweng
 *
 */
public class CharType implements FieldType<char[]> {

    @Override
    public final char[] get(final ResultSet rs, final int index)
        throws SQLException {
        return rs.getString(index).toCharArray();
    }

    @Override
    public final void set(final PreparedStatement ps, final int index,
        final char[] object) throws SQLException {
        ps.setCharacterStream(index, new CharArrayReader(object));
    }
}
