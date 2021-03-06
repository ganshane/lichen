package lichen.creeper.core.internal.shiro;

import lichen.core.services.LichenException;
import lichen.creeper.core.services.CreeperCoreExceptionCode;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author jcai
 */
abstract class AbstractShiroAnnotationWorker implements ComponentClassTransformWorker2 {
    private AuthorizingAnnotationMethodInterceptor _interceptor;

    private final MethodAdvice advice = new MethodAdvice()
    {
        public void advise(final MethodInvocation invocation)
        {
            try
            {
                _interceptor.invoke(new org.apache.shiro.aop.MethodInvocation() {
                    @Override
                    public Object proceed() throws Throwable {
                        invocation.proceed();
                        return invocation.getReturnValue();
                    }

                    @Override
                    public Method getMethod() {
                        return invocation.getMethod();
                    }

                    @Override
                    public Object[] getArguments() {
                        return null;
                    }

                    @Override
                    public Object getThis() {
                        return invocation.getInstance();
                    }
                });
            }catch(UnauthenticatedException ue){
                throw new LichenException(ue.getMessage(),CreeperCoreExceptionCode.ACCESS_DENIED);
            } catch (Throwable ex)
            {
                throw LichenException.wrap(ex);
            }
        }
    };

    protected AbstractShiroAnnotationWorker(Subject subject) {
        _interceptor = createInterceptor(subject);
    }

    @Override
    public void transform(PlasticClass plasticClass, TransformationSupport support, MutableComponentModel model) {
    	//实现在page类上加权限注解的拦截功能。component不包含在内。
    	if(plasticClass.hasAnnotation(getAnnotationClass()) && plasticClass.getClassName().contains("pages")){
    		List<PlasticMethod> methods = plasticClass.getMethods();
    		for(PlasticMethod method : methods){
    			method.addAdvice(advice);
    		}
    	}
    		
        for (PlasticMethod method : plasticClass.getMethodsWithAnnotation(getAnnotationClass()))
        {
            method.addAdvice(advice);
        }
    }
    protected abstract AuthorizingAnnotationMethodInterceptor createInterceptor(Subject _subject);
    protected abstract <T extends Annotation> Class<T> getAnnotationClass();
}
