package lichen.ar.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 定义某一字段的类型,用来从数据库中取得对象以及从设置数据库对象
 * @author jcai
 */
public interface FieldType<T>{
    /**
     * 设置字段的对象
     * @param ps PreparedStatement对象
     * @param index 索引序列
     * @param object 对应的值
     * @throws SQLException 发生数据库异常
     */
    public void set(PreparedStatement ps,int index,T object) throws SQLException;

    /**
     * 从数据库中得到某一个值
     * @param rs 结果集对象
     * @param index 结果集中索引
     * @return 对象值
     * @throws SQLException 发生数据库异常
     */
    public T get(ResultSet rs,int index) throws SQLException;
}
