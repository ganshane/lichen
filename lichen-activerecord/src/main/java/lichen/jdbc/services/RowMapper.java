package lichen.jdbc.services;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 针对数据库每行数据进行mapper
 * @author jcai
 * @param <T> 对应处理完行后返回的结果
 */
public interface RowMapper<T>{
    /**
     * 对每行数据进行map
     * @param rs 结果集对象
     * @param index 行数的索引，从0开始
     * @return map行数据之后的结果
     * @throws SQLException 发生数据库操作异常
     */
    public T mapRow(ResultSet rs,int index) throws SQLException;
}
