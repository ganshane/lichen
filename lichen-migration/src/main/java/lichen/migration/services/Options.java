// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.services;

import lichen.migration.model.*;

/**
 * @author jcai
 */
public interface Options {
    /**
     * 构造默认值对象
     * @param value 默认值
     * @return 默认值对象
     */
    Default Default(String value);

    /**
     * 定义列的长度
     * @param length 列的长度
     * @return 长度定义实例
     */
    Limit Limit(int length);

    /**
     * 制定数字列的精度
     * @param precision 精度
     * @return 列的精度
     */
    Precision Precision(int precision);

    /**
     * 定义列的刻度
     * @param scale 列的刻度
     * @return 列的刻度
     */
    Scale Scale(int scale);

    /**
     * 非空
     * @return 非空的设置
     */
    NotNull NotNull();

    /**
     * 可为空
     * @return 可为空
     */
    Nullable Nullable();

    /**
     * 主键
     * @return 主键
     */
    PrimaryKey PrimaryKey();

    /**
     * 唯一
     * @return 唯一对象
     */
    Unique Unique();

    /**
     * 自增长列
     * @return 自增长列
     */
    AutoIncrement AutoIncrement();

    /**
     * 针对索引或者外键的命名
     * @param name 名称
     * @return Name配置
     */
    Name Name(String name);
}
