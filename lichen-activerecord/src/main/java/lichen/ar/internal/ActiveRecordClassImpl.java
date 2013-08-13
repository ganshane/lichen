package lichen.ar.internal;

import lichen.ar.model.Field;
import lichen.ar.services.ActiveRecordErrorCode;
import lichen.ar.services.PersisterHelper;
import lichen.core.services.LichenException;
import lichen.core.services.WordUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 抽象class的实现类.
 * active record的基础实现类
 * @author jcai
 * @param <PKValue> 对应的主键值
 */
public abstract class ActiveRecordClassImpl<PKValue> {
    /** 主键字段名称. **/
    private String pkFieldName = "id";
    /** 对应的表名. **/
    private String tableName;
    /** 对应数据库的列名,自动初始化. **/
    private Set<String> columns;
    /** 保存实体对应的值. **/
    private Map<String, Object> values = new HashMap<String, Object>();
    /** 修改后的列的集合. **/
    private Set<String> columnsModified = new HashSet<String>();

    private final Map<String, Field<?>> fields;
    /**
     * @param persisterHelper persisterHelper
     */
    protected ActiveRecordClassImpl(final PersisterHelper persisterHelper) {
        tableName = WordUtil.tableize(getClass().getSimpleName());
        fields = persisterHelper.findTableFields(tableName);
    }

    /**
     * 设置数据.
     * @param fieldName 列名
     * @param fieldValue 列的值
     * @return 之前设置的值
     */
    public final Object setData(final String fieldName,
            final Object fieldValue) {
        if (!columns.contains(fieldName)) {
            throw new LichenException(ActiveRecordErrorCode.COLUMN_NOT_EXISTS);
        }
        //TODO 要不要判断旧的值和新值是否相等?
        columnsModified.add(fieldName);
        return values.put(fieldName, fieldValue);
    }

    public final Map<String, Field<?>> getFields() {
        return fields;
    }

    public final String getPkFieldName() {
        return pkFieldName;
    }
}
