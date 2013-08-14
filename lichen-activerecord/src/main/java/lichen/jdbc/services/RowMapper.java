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
package lichen.jdbc.services;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 针对数据库每行数据进行mapper.
 * @author jcai
 * @param <T> 对应处理完行后返回的结果
 */
public interface RowMapper<T> {
    /**
     * 对每行数据进行map.
     * @param rs 结果集对象
     * @param index 行数的索引，从0开始
     * @return map行数据之后的结果
     * @throws SQLException 发生数据库操作异常
     */
    T mapRow(ResultSet rs, int index) throws SQLException;
}
