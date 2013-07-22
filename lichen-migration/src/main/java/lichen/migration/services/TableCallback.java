package lichen.migration.services;

import lichen.migration.model.TableDefinition;

/**
 * 表的回调类
 * @author jcai
 */
public interface TableCallback {
    /**
     * 创建表时候的动作
     * @param t 表的定义
     */
    public void doInTable(TableDefinition t) throws Throwable;
}
