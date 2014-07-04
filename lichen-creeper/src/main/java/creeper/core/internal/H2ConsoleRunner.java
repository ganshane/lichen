package creeper.core.internal;

import creeper.core.services.CreeperException;

import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * h2 console runner
 * @author jcai
 */
public class H2ConsoleRunner {
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void run(Connection conn){
        /*
        */
        try {
            Class clazz = Class.forName("org.h2.tools.Server");
            Method method = clazz.getMethod("createWebServer",String[].class);
            Object[] param = new Object[1];
            param[0] = null;
            Object obj = method.invoke(clazz,param);
            Method startMethod = clazz.getMethod("start");
            startMethod.invoke(obj);

            Method getServiceMethod = clazz.getMethod("getService");
            Object service = getServiceMethod.invoke(obj);
            Class clazz2 = Class.forName("org.h2.server.web.WebServer");
            Method addSessionMethod = clazz2.getMethod("addSession", Connection.class);
            Object url = addSessionMethod.invoke(service,conn);

            method = clazz.getMethod("openBrowser",String.class);
            method.invoke(null, url);
        } catch (Throwable e) {
            throw CreeperException.wrap(e);
        }
         /*
        Server server = Server.createWebServer();
        server.start();
        WebServer service = (WebServer) server.getService();
        String url = service.addSession(dataSource.getConnection());
        Server.openBrowser(url);
        */
    }
}
