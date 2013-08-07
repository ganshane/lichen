package lichen.ar.services;

import lichen.ar.model.Field;

import java.util.Map;

/**
 * 持久化的帮助类
 * @author jcai
 */
public interface PersisterHelper {
    /**
     * 通过查找数据库表，自动获得表的映射
     * @param tableName 表名
     * @return key为转换为字段名称，Field为对应属性定义值
     */
    Map<String,Field<?>> findTableFields(String tableName);
}
