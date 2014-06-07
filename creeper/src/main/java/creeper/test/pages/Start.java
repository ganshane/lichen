package creeper.test.pages;

import creeper.test.dao.EntityTestDao;
import creeper.test.entities.EntityA;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.util.TextStreamResponse;

import javax.inject.Inject;

/**
 * @author jcai
 */
public class Start {
    @Inject
    private EntityTestDao _dao;
    @Inject
    private Subject _subject;
    @Inject
    private WebSecurityManager _securityManager;
    @RequiresUser
    public Object onActivate(){
        UsernamePasswordToken token = new UsernamePasswordToken("bjangles", "dance");
        token.setRememberMe(true);

        _subject.login(token);

        EntityA entityA = new EntityA();
        _dao.save(entityA);
        return new TextStreamResponse("text/plain","hello");
    }
}
