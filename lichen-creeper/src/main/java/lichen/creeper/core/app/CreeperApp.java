package lichen.creeper.core.app;

import lichen.creeper.core.CreeperCoreModule;
import lichen.server.services.LichenWebServer;
import org.apache.tapestry5.TapestryFilter;
import org.eclipse.jetty.server.Server;

import javax.servlet.ServletContext;

/**
 * lichen.creeper application
 */
public class CreeperApp {
    public static void main(String[] args) throws Exception {
        Server server = LichenWebServer.
                createTapestryWebapp(8080, "lichen.creeper.core", "creeper", CreeperTapestryFilter.class.getName());
        server.start();
    }
    public static class CreeperTapestryFilter extends TapestryFilter{
        @Override
        protected Class[] provideExtraModuleClasses(ServletContext context) {
            return new Class[]{CreeperCoreModule.class};
        }
    }
}
