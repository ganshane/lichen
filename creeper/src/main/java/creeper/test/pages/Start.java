package creeper.test.pages;

import org.apache.tapestry5.util.TextStreamResponse;

/**
 * @author jcai
 */
public class Start {
    public Object onActivate(){
        return new TextStreamResponse("text/plain","hello");
    }
}
