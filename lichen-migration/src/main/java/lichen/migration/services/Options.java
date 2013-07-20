package lichen.migration.services;

import lichen.migration.model.*;

/**
 * 定义所有的接口
 * @author jcai
 */
public class Options {
    /**
     * 构造默认值对象
     * @param value 默认值
     * @return 默认值对象
     */
    public static Default Default(final String value){
        return new Default(){
            public String getValue() {
                return value;
            }
        };
    }

    /**
     * 定义列的长度
     * @param length 列的长度
     * @return 长度定义实例
     */
    public static Limit Limit(final int length){
        return new Limit() {
            public int getValue() {
                return length;
            }
        };
    }
    /**
     * 指定列不能为空
     */
    public static NotNull NotNull = new NotNull() {};
    /**
     * 指定列可以为空
     */
    public static Nullable Nullable= new Nullable() {};

    /**
     * 制定数字列的精度
     * @param precision 精度
     * @return 列的精度
     */
    public static Precision Precision(final int precision){
        return new Precision() {
            public int getValue() {
                return precision;
            }
        };
    }

    /**
     * 定义列的刻度
     * @param scale 列的刻度
     * @return 列的刻度
     */
    public static Scale Scale(final  int scale){
        return new Scale() {
            public int getValue() {
                return scale;
            }
        };
    }
    /**
     * 定义主键列
     */
    public static PrimaryKey PrimaryKey = new PrimaryKey() {};
    /** 定义列或者键的唯一性 **/
    public static Unique Unique = new Unique() {};
    /** 定义某一列为自增长字段 **/
    public static AutoIncrement AutoIncrement = new AutoIncrement() {};
}
