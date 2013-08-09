package lichen.ar.internal.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#TIME} 成 {@link java.util.Date} 对象
 * @author weiweng
 *
 */
public class TimeType implements FieldType<Date> {

	@Override
	public Date get(ResultSet rs, int index) throws SQLException {
		return rs.getTime(index);
	}

	@Override
	public void set(PreparedStatement ps, int index, Date object)
			throws SQLException {
		ps.setTime(index, new Time(object.getTime()));
	}
}