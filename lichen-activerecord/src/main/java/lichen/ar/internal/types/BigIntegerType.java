// Copyright 2013 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package lichen.ar.internal.types;

import lichen.ar.services.FieldType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 映射数据库中的{@link java.sql.Types#BIGINT} 成 {@link java.math.BigInteger} 对象.
 *
 * @author jcai
 */
public class BigIntegerType implements FieldType<BigInteger> {
    @Override
    public void set(PreparedStatement ps, int index, BigInteger value)
        throws SQLException {
        ps.setBigDecimal(index, new BigDecimal(value));
    }

    @Override
    public BigInteger get(ResultSet rs, int index) throws SQLException {
        return rs.getBigDecimal(index).toBigIntegerExact();
    }
}
