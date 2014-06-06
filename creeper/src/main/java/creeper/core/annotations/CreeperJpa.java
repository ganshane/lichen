package creeper.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * @author jcai
 */
@Target( { PARAMETER, FIELD, CONSTRUCTOR ,TYPE})
@Retention(RUNTIME)
@Documented
public @interface CreeperJpa {
}
