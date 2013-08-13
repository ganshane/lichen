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
package lichen.ar.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 定义某一字段的类型,用来从数据库中取得对象以及从设置数据库对象.
 *
 * @author jcai
 */
public interface FieldType<T> {
    /**
     * 设置字段的对象.
     *
     * @param ps     PreparedStatement对象
     * @param index  索引序列
     * @param object 对应的值
     * @throws SQLException 发生数据库异常.
     */
    void set(PreparedStatement ps, int index, T object) throws SQLException;

    /**
     * 从数据库中得到某一个值.
     *
     * @param rs    结果集对象
     * @param index 结果集中索引
     * @return 对象值
     * @throws SQLException 发生数据库异常
     */
    T get(ResultSet rs, int index) throws SQLException;
}
