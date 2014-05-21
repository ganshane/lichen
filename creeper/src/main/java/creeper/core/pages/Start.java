package creeper.core.pages;

import org.apache.tapestry5.util.TextStreamResponse;

public class Start {
    Object onActivate(){
        return new TextStreamResponse("text/plain","hello world!");
    }
}
