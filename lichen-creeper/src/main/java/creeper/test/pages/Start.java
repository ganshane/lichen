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
    @RequiresUser
    public Object onActivate(){
        return new TextStreamResponse("text/plain","hello");
    }
}
