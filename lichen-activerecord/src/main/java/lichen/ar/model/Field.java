package lichen.ar.model;

import lichen.ar.services.FieldType;

/**
 * 针对对象的字段定义.
 * @author jcai
 */
public class Field<T> {
    //字段类型
    private FieldType<T> fieldType;
    //字段名称
    private String fieldName;
    //列名
    private String columnName;

    public final void setFieldType(final FieldType<T> vfieldType) {
        this.fieldType = vfieldType;
    }
    public final FieldType<T> getFieldType() {
        return fieldType;
    }
    public final void setFieldName(final String vfieldName) {
        this.fieldName = vfieldName;
    }
    public final String getFieldName() {
        return fieldName;
    }
    public final void setColumnName(final String vcolumnName) {
        this.columnName = vcolumnName;
    }
    public final String getColumnName() {
        return columnName;
    }
}
