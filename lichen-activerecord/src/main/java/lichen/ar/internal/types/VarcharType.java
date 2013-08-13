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