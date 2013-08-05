package lichen.ar.internal.types;

import lichen.ar.services.FieldType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 映射数据库中的{@link java.sql.Types#BIGINT} 成 {@link java.math.BigInteger} 对象
 * @author jcai
 */
public class BigIntegerType implements FieldType<BigInteger> {
    @Override
    public void set(PreparedStatement ps, int index, BigInteger value) throws SQLException {
        ps.setBigDecimal( index, new BigDecimal(value));
    }

    @Override
    public BigInteger get(ResultSet rs, int index) throws SQLException {
        return rs.getBigDecimal(index).toBigIntegerExact();
    }
}
