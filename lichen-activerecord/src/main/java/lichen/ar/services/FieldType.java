package lichen.ar.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 定义某一字段的类型
 * @author jcai
 */
public interface FieldType<T>{
    /**
     * 设置字段的对象
     * @param ps
     * @param index
     * @param object
     * @throws SQLException
     */
    public void set(PreparedStatement ps,int index,T object) throws SQLException;
    public T get(ResultSet rs,int index) throws SQLException;
}
