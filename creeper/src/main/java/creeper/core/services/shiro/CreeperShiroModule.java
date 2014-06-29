package creeper.core.services.shiro;

import creeper.core.annotations.CreeperJpa;
import creeper.core.internal.override.TapestrySessionFactoryWithShiroSession;
import creeper.core.internal.shiro.JpaRealm;
import creeper.core.internal.shiro.RequiresPermissionsWorker;
import creeper.core.internal.shiro.RequiresRolesWorker;
import creeper.core.internal.shiro.RequiresUserWorker;
import creeper.core.services.jpa.SpringDataDaoProvider;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.tapestry5.internal.services.TapestrySessionFactory;
import org.apache.tapestry5.ioc.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.internal.services.RegistryStartup;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

import javax.persistence.EntityManager;
import java.util.Collection;

/**
 * 集成shiro的模块
 * @author jcai
 */
public class CreeperShiroModule {
    public static void bind(ServiceBinder binder){
        //构建密码服务
        binder.bind(PasswordService.class, DefaultPasswordService.class);
        binder.bind(CredentialsMatcher.class,PasswordMatcher.class);
    }
    @Contribute(WebSecurityManager.class)
    public static void provideJpaRealm(Configuration<Realm> realms,
                                       CredentialsMatcher passwordMatcher,
                                       @Autobuild JpaRealm realm){
        //JpaRealm realm = locator.autobuild(JpaRealm.class);
        realm.setCredentialsMatcher(passwordMatcher);
        realms.add(realm);
    }
    //@EagerLoad
    public static WebSecurityManager buildWebSecurityManager(@CreeperJpa EntityManager entityManager,Collection<Realm> realms){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(realms);
        return securityManager;
    }
    @Contribute(Runnable.class)
    public static void initSecurity(OrderedConfiguration<Runnable> configuration, final WebSecurityManager securityManager){
        configuration.add("jpa",new Runnable() {
            @Override
            public void run() {
                SecurityUtils.setSecurityManager(securityManager);
            }
        },"after:jpa");
    }
    @Scope(ScopeConstants.PERTHREAD)
    //@EagerLoad
    public static Subject buildSubject(@Local WebSecurityManager securityManager,RequestGlobals requestGlobals){
        //SecurityUtils.setSecurityManager(securityManager);
        Subject subject = new WebSubject.Builder(securityManager,requestGlobals.getHTTPServletRequest(), requestGlobals.getHTTPServletResponse()).buildWebSubject();
        ThreadContext.bind(subject);
        return subject;
    }
    @Contribute(ComponentClassTransformWorker2.class)
    public static void provideShiroTransformWorkers(
            OrderedConfiguration<ComponentClassTransformWorker2> configuration){
        configuration.addInstance("RequiresUser",RequiresUserWorker.class);
        configuration.addInstance("RequiresRoles",RequiresRolesWorker.class);
        configuration.addInstance("RequiresPermissions",RequiresPermissionsWorker.class);
    }
}
