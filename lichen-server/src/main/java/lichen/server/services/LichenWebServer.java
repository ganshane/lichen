package lichen.server.services;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.Filter;

/**
 * lichen web container server.
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 */
public class LichenWebServer {
    /**
     * 通过给定的webapp的路径创建web应用.
     * <p>
     * 创建完服务器之后，通过
     * <pre>
     *         server.start();
     *         server.join();
     *     </pre>
     * 来启动服务器
     * </p>
     *
     * @param webappPath  webapp的路径，通常包含了JSP和WEB-INF/web.xml的文件路径
     * @param port        端口
     * @param contextPath 监听的servelt的context path
     * @return jetty服务器实例
     */
    public static Server createServer(String webappPath,
                               int port,
                               String contextPath) {
        Server server = new Server(port);

        WebAppContext context = new WebAppContext();
        context.setWar(webappPath);
        context.setContextPath(contextPath);
        context.setParentLoaderPriority(true);
        server.setHandler(context);
        return server;
    }

    /**
     * 创建tapestry的webapp程序
     * @param port 端口
     * @param pkg tapestry package
     * @return 服务器实例
     */
    public static Server createTapestryWebapp(int port,String pkg,String appName){
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setDisplayName(appName);
        context.setInitParameter("tapestry.app-package",pkg);
        //default servlet holder
        ServletHolder servletHolder = new ServletHolder(DefaultServlet.class);
        servletHolder.setName("default");
        context.addServlet(servletHolder, "/");

        //tapestry filter
        FilterHolder filterHolder = null;
        try {
            filterHolder = new FilterHolder((Class<? extends Filter>) Thread.currentThread().getContextClassLoader().loadClass("org.apache.tapestry5.TapestryFilter"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        filterHolder.setName(appName);
        context.addFilter(filterHolder, "/*", FilterMapping.ALL);
        
//        server.addLifeCycleListener(listener);
        
        server.setHandler(context);
        return server;
    }
    
    /**
     * 创建tapestry的webapp程序
     * @param port 端口
     * @param pkg tapestry package
     * @param appName 服务器实例
     * @param filter filter全路径
     * @return
     */
    public static Server createTapestryWebapp(int port,String pkg,String appName,String filter){
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setDisplayName(appName);
        context.setInitParameter("tapestry.app-package",pkg);
        //default servlet holder
        ServletHolder servletHolder = new ServletHolder(DefaultServlet.class);
        servletHolder.setName("default");
        context.addServlet(servletHolder, "/");

        //tapestry filter
        FilterHolder filterHolder = null;
        try {
            filterHolder = new FilterHolder((Class<? extends Filter>) Thread.currentThread().getContextClassLoader().loadClass(filter));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        filterHolder.setName(appName);
        context.addFilter(filterHolder, "/*", FilterMapping.ALL);
        server.setHandler(context);
        return server;
    }
    /**
     * 通过给定的web.xml以及webapp的路径创建web应用.
     * <p>
     * 创建完服务器之后，通过
     * <pre>
     *         server.start();
     *         server.join();
     * </pre>
     * 来启动服务器.
     * </p>
     *
     * @param webXmlPath  web.xml的路径
     * @param webappPath  webapp的路径，通常包含了JSP和WEB-INF/web.xml的文件路径
     * @param port        端口
     * @param contextPath 监听的servelt的context path
     * @return jetty服务器实例
     */
    public static Server createServer(String webXmlPath,
                               String webappPath,
                               int port,
                               String contextPath) {
        Server server = new Server(port);

        WebAppContext context = new WebAppContext();
        context.setDescriptor(webXmlPath);
        context.setResourceBase(webappPath);
        context.setContextPath(contextPath);
        context.setParentLoaderPriority(true);
        server.setHandler(context);
        return server;
    }
}
