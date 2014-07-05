package lichen.creeper.core.internal.shiro;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.UserAnnotationHandler;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;

/**
 * 对@RequiresUser的动态实现
 * @author jcai
 */
public class RequiresUserWorker extends AbstractShiroAnnotationWorker{
    public RequiresUserWorker(Subject subject) {
        super(subject);
    }
    @Override
    protected AuthorizingAnnotationMethodInterceptor createInterceptor(final Subject _subject) {
        return new AuthorizingAnnotationMethodInterceptor(new UserAnnotationHandler(){
            @Override
            protected Subject getSubject() {
                return _subject;
            }
        }){
            @Override
            protected Subject getSubject() {
                return _subject;
            }
        };
    }

    @Override
    protected <T extends Annotation> Class<T> getAnnotationClass() {
        return (Class<T>) RequiresUser.class;
    }
}
