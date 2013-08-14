package lichen.server.services;

import org.eclipse.jetty.server.Server;
import org.junit.Test;

/**
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 */
public class LichenWebServerTest {
    @Test
    public void test_app1() throws Exception {
        String file = getClass().getResource("/app1").getFile();
        LichenWebServer server = new LichenWebServer();
        Server jettyServer = server.createServer(
                file+"/config/web.xml",
                file+"/webapp",
                9900,
                "/"
                );
        jettyServer.start();
        //jettyServer.join();
        jettyServer.stop();
    }
    @Test
    public void test_app2() throws Exception {
        String file = getClass().getResource("/app2").getFile();
        LichenWebServer server = new LichenWebServer();
        Server jettyServer = server.createServer(
                file,
                9900,
                "/"
        );
        jettyServer.start();
        //jettyServer.join();
        jettyServer.stop();
    }
}
