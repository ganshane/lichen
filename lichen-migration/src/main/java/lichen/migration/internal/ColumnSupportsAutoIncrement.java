package lichen.migration.internal;

import java.lang.annotation.*;

/**
 * 列支持自增长
 * @author jcai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface ColumnSupportsAutoIncrement {
}
