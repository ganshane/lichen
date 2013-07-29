package lichen.ar.internal;

import lichen.ar.services.ActiveRecordErrorCode;
import lichen.core.services.LichenException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 抽象class的实现类
 * active record的基础实现类
 * @author jcai
 * @param <T> 实体类
 * @param <PKValue> 对应的主键值
 */
public abstract class ActiveRecordClassImpl<T,PKValue>{
    /** 主键字段名称 **/
    protected String pk = "id";
    /** 对应的表名 **/
    protected String tableName;
    /** 对应数据库的列名,自动初始化 **/
    Set<String> columns;
    /** 保存实体对应的值 **/
    Map<String,Object> values = new HashMap<String, Object>();
    /** 修改后的列的集合 **/
    private Set<String> columnsModified = new HashSet<String>();

    /**
     * 设置数据
     * @param fieldName 列名
     * @param fieldValue 列的值
     * @return 之前设置的值
     */
    public Object setData(String fieldName, Object fieldValue){
        if(!columns.contains(fieldName)){
            throw new LichenException(ActiveRecordErrorCode.COLUMN_NOT_EXISTS);
        }
        //TODO 要不要判断旧的值和新值是否相等?
        columnsModified.add(fieldName);
        return values.put(fieldName,fieldValue);
    }
}
