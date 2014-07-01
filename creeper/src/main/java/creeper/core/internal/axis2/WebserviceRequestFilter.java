package creeper.core.internal.axis2;

import creeper.core.services.CreeperException;
import creeper.core.services.axis2.WebServicePublisher;
import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.AxisServlet;
import org.apache.tapestry5.TapestryFilter;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 实现针对axis2的集成，便于使用tapestry能够自动导出一些webservice的服务类
 * @author jcai
 */
public class WebserviceRequestFilter implements HttpServletRequestFilter{
    private final AxisServlet _axisServlet;
    public WebserviceRequestFilter(final ApplicationGlobals applicationGlobals) throws ServletException {
        _axisServlet = new AxisServlet(){
            @Override
            public void init(ServletConfig config) throws ServletException {
                super.init(config);
                try {
                    //add tapestry ioc object supplier
                    axisConfiguration.addParameter("ServiceObjectSupplier", TapestryIOCObjectSupplier.class.getName());
                    //axisConfiguration.addParameter("EnableChildFirstClassLoading",true);
                    //get ioc registry object
                    Registry registry = (Registry) config.getServletContext().getAttribute(TapestryFilter.REGISTRY_CONTEXT_NAME);
                    if(registry != null){
                        //publish  service object annotioned by @WebService
                        WebServicePublisher publisher = registry.getService(WebServicePublisher.class);
                        publisher.registryWebServiceObject(axisConfiguration);
                    }

                } catch (AxisFault e) {
                    throw new RuntimeException(e);
                }
            }
        };
        ServletConfig config = new ServletConfig() {
            @Override
            public String getServletName() {
                return "axis";
            }

            @Override
            public ServletContext getServletContext() {
                return applicationGlobals.getServletContext();
            }

            @Override
            public String getInitParameter(String s) {
                return null;
            }

            @Override
            public Enumeration getInitParameterNames() {
                return null;
            }
        };
        _axisServlet.init(config);
    }
    @Override
    public boolean service(HttpServletRequest request, HttpServletResponse response, HttpServletRequestHandler handler) throws IOException {
        if(request.getServletPath().startsWith("/ws")){
            try {
                _axisServlet.service(request,response);
            } catch (ServletException e) {
                throw CreeperException.wrap(e);
            }
            return true;
        }
        return handler.service(request,response);
    }
}
