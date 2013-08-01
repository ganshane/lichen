package lichen.jdbc.services;

import java.util.List;

/**
 * 针对数据库操作的帮助类.
 * 常用方法
 * {@link #beginTransaction()} 和 {@link #commitTransaction()};
 * {@link #holdConnection()} 和 {@link #releaseConnection()}
 * 为成对出现
 * @author jcai
 */
public interface JdbcHelper {
    /**
     * 启动事务,在启动事务的时候需要注意，一定要运行{@link #commitTransaction()}
     * @see #commitTransaction()
     */
    public void beginTransaction();

    /**
     * 提交事务,在{@link #beginTransaction()}后需要调用此方法进行提交
     */
    public void commitTransaction();

    /**
     * 绑定connection对象到当前线程。
     * 如果当前线程已经绑定了事务，则使用事务绑定的连接，如果未绑定则启动新事务，并且把autoCommit设置为true
     * @see #beginTransaction()
     * @see #commitTransaction()
     */
    public void holdConnection();

    /**
     * 释放连接,如果当前事务为非自动提交模式，则{@link #commitTransaction() 提交事务}
     * 如果为自动提交模式，则关闭当前连接
     */
    public void releaseConnection();

    /**
     * 执行sql对象
     * @param sql sql语句
     * @param params 待执行的参数
     * @return 执行的结果
     */
    public int execute(String sql,Object ... params);

    /**
     * 查询出来一个list对象
     * @param sql 待查询语句
     * @param mapper 行的mapper对象
     * @param <T> 返回的结果对象类型
     * @return list对象
     */
    public <T> List<T> queryForList(String sql,RowMapper<T> mapper);
}
