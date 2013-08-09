package lichen.ar.internal.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#DATE} 成 {@link java.util.Date} 对象
 * @author weiweng
 *
 */
public class DateType implements FieldType<Date> {

	@Override
	public Date get(ResultSet rs, int index) throws SQLException {
		return rs.getDate(index);
	}

	@Override
	public void set(PreparedStatement ps, int index, Date object)
			throws SQLException {
		ps.setDate(index, new java.sql.Date(object.getTime()));
	}
}