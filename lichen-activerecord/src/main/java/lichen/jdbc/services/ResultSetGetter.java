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
 * 从{@link java.sql.ResultSet}读取值.
 * @author jcai
 */
public interface ResultSetGetter<T> {
    /**
     * 从ResultSet结果集中获取值.
     * @param rs ResultSet对象
     * @param index 结果集索引序列值
     * @return 得到的对象值
     * @throws SQLException 发送数据库操作错误
     */
    T get(ResultSet rs, int index) throws SQLException;
}
