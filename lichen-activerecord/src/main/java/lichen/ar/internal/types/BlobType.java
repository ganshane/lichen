package lichen.ar.internal.types;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#BLOB} 成 {@link java.sql.Blob} 对象
 * @author weiweng
 *
 */
public class BlobType implements FieldType<Blob> {

	@Override
	public Blob get(ResultSet rs, int index) throws SQLException {
		return rs.getBlob(index);
	}

	@Override
	public void set(PreparedStatement ps, int index, Blob object)
			throws SQLException {
		ps.setBlob(index, object);
	}
}