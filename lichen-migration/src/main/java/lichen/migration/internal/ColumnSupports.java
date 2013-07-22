package lichen.migration.internal;

import java.lang.annotation.*;

/**
 * @author jcai
 */
class ColumnSupports {
}
/**
 * 列支持自增长
 * @author jcai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@interface ColumnSupportsAutoIncrement {
}

/**
 * 列是否支持默认值定义
 * @author jcai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@interface ColumnSupportsDefault {
}

/**
 * 列是否支持长度定义
 * @author jcai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@interface ColumnSupportsLimit {
}

/**
 * 列是否支持精度定义
 * @author jcai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@interface ColumnSupportsPrecision {
}

/**
 * 列是否支持刻度定义
 * @author jcai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@interface ColumnSupportsScale {
}

