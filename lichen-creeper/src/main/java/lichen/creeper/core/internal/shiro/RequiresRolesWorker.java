package lichen.creeper.core.internal.shiro;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.RoleAnnotationHandler;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;

/**
 * 实现shiro的RequiresRoles的拦截
 * @author jcai
 */
public class RequiresRolesWorker extends AbstractShiroAnnotationWorker{
    public RequiresRolesWorker(Subject subject) {
        super(subject);
    }

    @Override
    protected AuthorizingAnnotationMethodInterceptor createInterceptor(final Subject _subject) {
        return new AuthorizingAnnotationMethodInterceptor(new RoleAnnotationHandler(){
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
        return (Class<T>) RequiresRoles.class;
    }
}
