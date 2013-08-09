package lichen.ar.internal.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#DOUBLE} 成 {@link java.lang.Double} 对象
 * @author weiweng
 *
 */
public class DoubleType implements FieldType<Double> {

	@Override
	public Double get(ResultSet rs, int index) throws SQLException {
		return rs.getDouble(index);
	}

	@Override
	public void set(PreparedStatement ps, int index, Double object)
			throws SQLException {
		ps.setDouble(index, object);
	}
}