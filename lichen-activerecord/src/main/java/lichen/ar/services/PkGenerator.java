package lichen.ar.services;


import lichen.jdbc.services.JdbcHelper;

import java.io.Serializable;

/**
 * 主键的生成器.
 * @author jcai
 */
public interface PkGenerator {
    /**
     * 生成一个ID.
     * @param jdbcHelper jdbc helper class
     * @param entity 实体对象
     * @param <T> 操作实体的类型
     * @return 主键值
     */
    <T extends ActiveRecord> Serializable generate(JdbcHelper jdbcHelper,
            T entity);
}
