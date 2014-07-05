package lichen.creeper.core.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.services.ExceptionReporter;

import lichen.creeper.core.services.CreeperException;

/**
 * 错误异常页面类
 * @author jcai
 */
public class ExceptionPage implements ExceptionReporter {
    @SuppressWarnings("unused")
	@Property
    private CreeperException exception;

    @Override
    public void reportException(Throwable exception) {
        this.exception = (CreeperException) exception;
    }
}
