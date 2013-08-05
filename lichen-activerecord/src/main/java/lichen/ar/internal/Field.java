package lichen.ar.internal;

import lichen.ar.services.FieldType;

/**
 * 针对对象的字段定义
 * @author jcai
 */
class Field<T> {
    //字段类型
    FieldType<T> fieldType;
    //字段名称
    String fieldName;
    //列名
    String columnName;
}
