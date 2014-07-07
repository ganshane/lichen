package lichen.creeper.core.pages;

import lichen.core.services.LichenException;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.services.ExceptionReporter;


/**
 * 错误异常页面类
 * @author jcai
 */
public class ExceptionPage implements ExceptionReporter {
    @SuppressWarnings("unused")
	@Property
    private LichenException exception;

    @Override
    public void reportException(Throwable exception) {
        this.exception = (LichenException) exception;
    }
}
