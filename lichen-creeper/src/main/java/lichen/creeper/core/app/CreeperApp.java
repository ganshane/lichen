package lichen.creeper.core.app;

import javax.servlet.ServletContext;

import lichen.creeper.core.CreeperCoreModule;
import lichen.creeper.core.CreeperCoreSymbols;
import lichen.creeper.core.internal.utils.LogUtils;
import lichen.server.services.LichenWebServer;

import org.apache.tapestry5.TapestryFilter;
import org.eclipse.jetty.server.Server;

/**
 * lichen.creeper application
 */
public class CreeperApp {
    public static void main(String[] args) throws Exception {
    	//容器启动之前配置log4j
    	LogUtils.configLogger(CreeperCoreModule.buildCreeperCoreConfig(System.getProperty(CreeperCoreSymbols.SERVER_HOME)).log_file, "Creeper");
        Server server = LichenWebServer.
                createTapestryWebapp(Integer.valueOf(System.getProperty(CreeperCoreSymbols.SERVER_PORT, "8080")), "lichen.creeper.core", "creeper", CreeperTapestryFilter.class.getName());
        server.start();
    }
    public static class CreeperTapestryFilter extends TapestryFilter{
        @Override
        protected Class[] provideExtraModuleClasses(ServletContext context) {
            return new Class[]{CreeperCoreModule.class};
        }
    }
}
