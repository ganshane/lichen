package lichen.ar.internal;

import lichen.jdbc.services.JdbcHelper;

import java.util.Map;

/**
 * 针对数据库的操作类
 * @author jcai
 */
class Persister {
    //数据库操作类
    private final DatabaseAdapter databaseAdapter;
    Persister(DatabaseAdapter databaseAdapter){
        this.databaseAdapter = databaseAdapter;
    }
    Map<String,Field<?>> findTableMapping(String tableName){
        JdbcHelper helper = databaseAdapter.createJdbcHelper();
        return null;
    }
}
