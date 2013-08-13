package lichen.jdbc.services;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 针对通过数据库查询到的{@link java.sql.ResultSet}结果的处理方法.
 * @author jcai
 */
public interface ResultSetCallback<T> {
    /**
     * 获得一个{@link ResultSet} 对象的处理.
     * @param resultSet 查询结果集
     * @return 针对{@link ResultSet}的处理结果
     * @throws SQLException 发生数据库操作失败
     */
    T doInResultSet(ResultSet resultSet) throws SQLException;
}
