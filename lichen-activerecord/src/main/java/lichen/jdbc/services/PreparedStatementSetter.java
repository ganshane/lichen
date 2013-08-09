package lichen.jdbc.services;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 针对{@link java.sql.PreparedStatement}设置值
 * @author jcai
 */
public interface PreparedStatementSetter {
    /**
     * 对PreparedStatement设置值
     * @param ps {@link PreparedStatement}对象
     * @param index 设置的索引号
     * @throws SQLException 发生数据库操作错误
     */
    void set(PreparedStatement ps,int index) throws SQLException;
}
