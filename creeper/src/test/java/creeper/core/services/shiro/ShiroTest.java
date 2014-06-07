package creeper.core.services.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 集成apache的shiro进行测试
 * @author jcai
 */
public class ShiroTest {
    private static final Logger logger = LoggerFactory.getLogger(ShiroTest.class);
    @Test
    public void test_shiro(){
        // Using the IniSecurityManagerFactory, which will use the an INI file
        // as the security file.
        Ini ini= new Ini();
        ini.load(ShiroTest.class.getResourceAsStream("/test_shiro.ini"));
        Factory<org.apache.shiro.mgt.SecurityManager> factory =
                new IniSecurityManagerFactory(ini);

        // Setting up the SecurityManager...
        DefaultSecurityManager securityManager = (DefaultSecurityManager) factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject user = SecurityUtils.getSubject();

        logger.info("User is authenticated:  " + user.isAuthenticated());

        UsernamePasswordToken token = new UsernamePasswordToken("bjangles", "dance");
        token.setRememberMe(true);

        user.login(token);


        logger.info("User is authenticated:  " + user.isAuthenticated());
        logger.info("User is permit :  " + user.isPermitted("create-article"));
    }
}
