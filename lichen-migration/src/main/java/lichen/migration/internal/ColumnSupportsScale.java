package lichen.migration.internal;

import java.lang.annotation.*;

/**
 * 列是否支持刻度定义
 * @author jcai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface ColumnSupportsScale {
}
