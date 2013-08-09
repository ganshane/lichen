package lichen.jdbc.services;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 从{@link java.sql.ResultSet}读取值
 * @author jcai
 */
public interface ResultSetGetter<T> {
    /**
     * 从ResultSet结果集中获取值
     * @param rs ResultSet对象
     * @param index 结果集索引序列值
     * @return 得到的对象值
     * @throws SQLException 发送数据库操作错误
     */
    T get(ResultSet rs,int index) throws SQLException;
}
