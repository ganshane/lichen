package creeper.core.services.shiro;

import creeper.core.annotations.CreeperJpa;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

import javax.persistence.EntityManager;

/**
 * 集成shiro的模块
 * @author jcai
 */
public class CreeperShiroModule {
    public static void bind(ServiceBinder binder){
    }
    @EagerLoad
    public static WebSecurityManager buildWebSecurityManager(@CreeperJpa EntityManager entityManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(new JpaRealm());
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }
    @Scope(ScopeConstants.PERTHREAD)
    public static Subject buildSubject(@Local WebSecurityManager securityManager,RequestGlobals requestGlobals){
        SecurityUtils.setSecurityManager(securityManager);
        return new WebSubject.Builder(securityManager,requestGlobals.getHTTPServletRequest(), requestGlobals.getHTTPServletResponse()).buildWebSubject();
    }
    @Contribute(ComponentClassTransformWorker2.class)
    public static void provideShiroTransformWorkers(
            OrderedConfiguration<ComponentClassTransformWorker2> configuration){
        configuration.addInstance("RequiresUser",RequiresUserWorker.class);
        configuration.addInstance("RequiresRoles",RequiresRolesWorker.class);
        configuration.addInstance("RequiresPermissions",RequiresPermissionsWorker.class);
    }
}
