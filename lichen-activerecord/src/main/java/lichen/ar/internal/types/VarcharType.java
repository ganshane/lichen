package lichen.ar.internal.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#VARCHAR} 成 {@link java.lang.String} 对象
 * @author weiweng
 *
 */
public class VarcharType implements FieldType<String> {

	@Override
	public String get(ResultSet rs, int index) throws SQLException {
		return rs.getString(index);
	}

	@Override
	public void set(PreparedStatement ps, int index, String object)
			throws SQLException {
		ps.setString(index, object);
	}
}