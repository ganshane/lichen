package creeper.core.internal;

import creeper.core.services.CreeperException;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * 针对事物的AOP处理
 * @author jcai
 */
public class TransactionAdvice implements MethodAdvice{
    private final Method trueMethod;
    private TransactionInterceptor interceptor;

    public TransactionAdvice(@Local TransactionInterceptor interceptor,Method method) {
        this.interceptor = interceptor;
        this.trueMethod = method;
    }

    @Override
    public void advise(final MethodInvocation methodInvocation) {
        try {
            this.interceptor.invoke(new org.aopalliance.intercept.MethodInvocation() {

                @Override
                public Method getMethod() {
                    return trueMethod;
                    //return methodInvocation.getMethod();
                }

                @Override
                public Object[] getArguments() {
                    throw new UnsupportedOperationException();

                }

                @Override
                public Object proceed() throws Throwable {
                    methodInvocation.proceed();
                    return methodInvocation.getReturnValue();
                }

                @Override
                public Object getThis() {
                    return null;
                }

                @Override
                public AccessibleObject getStaticPart() {
                    throw new UnsupportedOperationException();
                }
            });
        } catch (Throwable throwable) {
            throw CreeperException.wrap(throwable);
        }
    }
}
