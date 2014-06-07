package creeper.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 针对权限控制的标签
 * @author jcai
 */
@Target({PARAMETER, FIELD, CONSTRUCTOR ,TYPE})
@Retention(RUNTIME)
@Documented
public @interface PermissionRequired {
    //permission 参数
    String value();
}
