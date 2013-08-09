package lichen.ar.internal.types;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#NUMERIC} 成 {@link java.math.BigDecimal} 对象
 * @author weiweng
 *
 */
public class NumericType implements FieldType<BigDecimal> {

	@Override
	public BigDecimal get(ResultSet rs, int index) throws SQLException {
		return rs.getBigDecimal(index);
	}

	@Override
	public void set(PreparedStatement ps, int index, BigDecimal object)
			throws SQLException {
		ps.setBigDecimal(index, object);
	}
}