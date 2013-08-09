package lichen.ar.internal.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#FLOAT} 成 {@link java.lang.Float} 对象
 * @author weiweng
 *
 */
public class FloatType implements FieldType<Float> {

	@Override
	public Float get(ResultSet rs, int index) throws SQLException {
		return rs.getFloat(index);
	}

	@Override
	public void set(PreparedStatement ps, int index, Float object)
			throws SQLException {
		ps.setFloat(index, object);
	}
}