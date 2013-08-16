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

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 针对通过数据库使用到的{@link java.sql.Connection}的处理方法.
 *
 * @author yangjm
 */
public interface ConnectionCallback<T> {
    /**
     * 获得一个{@link Connection} 对象的处理.
     *
     * @param conn 数据连接
     * @return 针对{@link Connection}的处理结果
     * @throws SQLException 发生数据库操作失败
     */
    T doInConnection(Connection conn) throws SQLException;
}
