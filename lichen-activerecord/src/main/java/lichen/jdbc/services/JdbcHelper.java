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

import java.util.List;

/**
 * 针对数据库操作的帮助类.
 * 常用方法
 * {@link #beginTransaction()} 和 {@link #commitTransaction()};
 * {@link #holdConnection()} 和 {@link #releaseConnection()}
 * 为成对出现
 *
 * @author jcai
 */
public interface JdbcHelper {
    /**
     * 启动事务,在启动事务的时候需要注意，一定要运行{@link #commitTransaction()}.
     *
     * @see #commitTransaction()
     */
    void beginTransaction();

    /**
     * 提交事务,在{@link #beginTransaction()}后需要调用此方法进行提交.
     */
    void commitTransaction();

    /**
     * 绑定connection对象到当前线程。.
     * 如果当前线程已经绑定了事务，则使用事务绑定的连接，如果未绑定则启动新事务，并且把autoCommit设置为true
     *
     * @see #beginTransaction()
     * @see #commitTransaction()
     */
    void holdConnection();

    /**
     * 释放连接,如果当前事务为非自动提交模式，则{@link #commitTransaction() 提交事务}.
     * 如果为自动提交模式，则关闭当前连接
     */
    void releaseConnection();

    /**
     * 执行sql对象.
     *
     * @param sql    sql语句
     * @param params 待执行的参数
     * @return 执行的结果
     */
    int execute(String sql, Object... params);

    /**
     * 可以传递参数的查询，查询结果是一个list.
     *
     * @param <T>     返回的结果对象类型
     * @param sql     含有占位符待查询语句
     * @param mapper  行的mapper对象
     * @param setters 给占位符赋值的对象
     * @return list对象
     */
    <T> List<T> queryForList(String sql, RowMapper<T> mapper,
                             PreparedStatementSetter... setters);

    /**
     * 返回查询的第一行.
     *
     * @param <T>     返回的结果对象类型
     * @param sql     待查询的语句
     * @param getter  取值的对象
     * @param setters 给占位符赋值的对象
     * @return getter返回的对象
     */
    <T> T queryForFirst(String sql, ResultSetGetter<T> getter,
                        PreparedStatementSetter... setters);

    /**
     * 带回调函数的查询.
     *
     * @param <T>      返回的结果对象类型
     * @param sql      待查询的语句
     * @param callback 回调函数
     * @param setters  给占位符赋值的对象
     * @return 通过回调函数处理后结果
     */
    <T> T withResultSet(String sql, ResultSetCallback<T> callback,
                        PreparedStatementSetter... setters);

}
