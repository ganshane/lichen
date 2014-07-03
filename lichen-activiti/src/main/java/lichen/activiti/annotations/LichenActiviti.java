package lichen.activiti.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标记对象为Creeper Activiti模块类.
 * <p>
 *     也可标记该服务类能够导出到activiti中
 * </p>
 * @author jcai
 */
@Target( { PARAMETER, FIELD, CONSTRUCTOR ,TYPE})
@Retention(RUNTIME)
@Documented
public @interface LichenActiviti {
}
