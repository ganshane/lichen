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

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 针对{@link java.sql.PreparedStatement}设置值.
 * @author jcai
 */
public interface PreparedStatementSetter {
    /**
     * 对PreparedStatement设置值.
     * @param ps {@link PreparedStatement}对象
     * @param index 设置的索引号
     * @throws SQLException 发生数据库操作错误
     */
    void set(PreparedStatement ps, int index) throws SQLException;
}
