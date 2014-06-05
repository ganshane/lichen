package creeper.core.app;

import lichen.server.services.LichenWebServer;
import org.eclipse.jetty.server.Server;

/**
 * creeper application
 */
public class CreeperApp {
    public static void main(String[] args) throws Exception {
        Server server = LichenWebServer.createTapestryWebapp(8080,"creeper.core","creeper");
        server.start();
    }
}
