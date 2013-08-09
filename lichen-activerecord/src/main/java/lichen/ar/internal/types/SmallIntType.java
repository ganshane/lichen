package lichen.ar.internal.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#SMALLINT} 成 {@link java.lang.Short} 对象
 * @author weiweng
 *
 */
public class SmallIntType implements FieldType<Short> {

	@Override
	public Short get(ResultSet rs, int index) throws SQLException {
		return rs.getShort(index);
	}

	@Override
	public void set(PreparedStatement ps, int index, Short object)
			throws SQLException {
		ps.setShort(index, object);
	}
}