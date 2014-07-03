package lichen.ws.internal;


import lichen.server.services.LichenWebServer;
import lichen.ws.LichenWsModule;
import lichen.ws.services.EchoService;
import lichen.ws.services.WebserviceClient;
import org.apache.tapestry5.TapestryFilter;
import org.eclipse.jetty.server.Server;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.ServletContext;

/**
 * @author jcai
 */
public class WebServiceTest{
    public static class WsFilter extends TapestryFilter{
        @Override
        protected Class[] provideExtraModuleClasses(ServletContext context) {
            return new Class[]{LichenWsModule.class};
        }
    }
    @Test
    public void testAxis2() throws Exception {
        Server server = LichenWebServer.createTapestryWebapp(1234, "lichen.ws", "ws",WsFilter.class.getName());
        server.start();

        EchoService hiService = WebserviceClient.
                createClient(EchoService.class,
                        "http://localhost:1234/ws/EchoService?wsdl",
                        "http://www.egfit.com/");
        //调用WebService方法
        Assert.assertEquals("xiaoming", hiService.echoString("xiaoming"));
        server.stop();
    }
}
