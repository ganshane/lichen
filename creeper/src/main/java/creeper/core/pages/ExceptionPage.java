package creeper.core.pages;

import creeper.core.services.CreeperException;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.services.ExceptionReporter;
import org.apache.tapestry5.util.TextStreamResponse;

/**
 * @author jcai
 */
public class ExceptionPage implements ExceptionReporter {
    @Property
    private CreeperException exception;

    @Override
    public void reportException(Throwable exception) {
        this.exception = (CreeperException) exception;
    }
}
