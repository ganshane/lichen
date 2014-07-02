package creeper.core.services.axis2;

import creeper.core.internal.axis2.EchoServiceImpl;
import creeper.core.internal.axis2.WebServicePublisherImpl;
import creeper.core.internal.axis2.WebserviceRequestFilter;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;

/**
 * 集成webservice
 * @author jcai
 */
public class Axis2Module {
    public static void bind(ServiceBinder binder){
        binder.bind(EchoService.class, EchoServiceImpl.class).withSimpleId();
        binder.bind(WebServicePublisher.class, WebServicePublisherImpl.class);
    }
    @Contribute(HttpServletRequestHandler.class)
    public static void provideAxis2WebService(OrderedConfiguration<HttpServletRequestFilter> configuration){
        configuration.addInstance("axis2", WebserviceRequestFilter.class,"before:GZIP");
    }
}
