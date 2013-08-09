package lichen.ar.internal.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#TIMESTAMP} 成 {@link java.util.Date} 对象
 * @author weiweng
 *
 */
public class TimeStampType implements FieldType<Date> {

	@Override
	public Date get(ResultSet rs, int index) throws SQLException {
		return rs.getTimestamp(index);
	}

	@Override
	public void set(PreparedStatement ps, int index, Date object)
			throws SQLException {
		ps.setTimestamp(index, new Timestamp(object.getTime()));
	}
}