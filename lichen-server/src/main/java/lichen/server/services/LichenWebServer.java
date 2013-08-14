package lichen.server.services;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

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
    public Server createServer(String webappPath,
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
    public Server createServer(String webXmlPath,
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
