package lichen.ar.services;

/**
 * active record基础类
 * @author jcai
 */
public abstract class ActiveRecord{
    protected String tableName;
    protected String pkField = "id";
    public Long save(){
        return -1l;
    }
    public int delete(){
        return 1;
    }
}
