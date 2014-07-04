package creeper.core.internal.shiro;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.PermissionAnnotationHandler;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;

/**
 * 实现基于shiro的RequiresPermissions的操作
 * @author jcai
 */
public  class RequiresPermissionsWorker extends AbstractShiroAnnotationWorker{
    public RequiresPermissionsWorker(Subject subject) {
        super(subject);
    }

    @Override
    protected AuthorizingAnnotationMethodInterceptor createInterceptor(final Subject _subject) {
        return new AuthorizingAnnotationMethodInterceptor(new PermissionAnnotationHandler(){
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
        return (Class<T>) RequiresPermissions.class;
    }
}
