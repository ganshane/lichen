package lichen.creeper.test.pages;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.util.TextStreamResponse;

/**
 * @author jcai
 */
public class Start {
    @RequiresUser
    public Object onActivate(){
        return new TextStreamResponse("text/plain","hello");
    }
}
