package lichen.ar.internal.types;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#BLOB} 成 {@link java.sql.Blob} 对象.
 * @author weiweng
 *
 */
public class BlobType implements FieldType<Blob> {

    @Override
    public final Blob get(final ResultSet rs, final int index)
        throws SQLException {
        return rs.getBlob(index);
    }

    @Override
    public final void set(final PreparedStatement ps, final int index,
            final Blob object) throws SQLException {
        ps.setBlob(index, object);
    }
}
