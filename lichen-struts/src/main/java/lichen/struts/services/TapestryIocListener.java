package lichen.struts.services;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.ioc.def.ModuleDef;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 集成了TapestryIoc的listener
 * @author jcai
 */
public class TapestryIocListener implements ServletContextListener{
    public static final String REGISTRY_CONTEXT_NAME = "org.apache.tapestry5.application-registry";
    private Registry registry;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        RegistryBuilder builder = new RegistryBuilder();
        for(ModuleDef def:provideExtraModuleDefs(context)){
            builder.add(def);
        }
        builder.add(provideExtraModuleClasses(context));
        registry = builder.build();
        servletContextEvent.getServletContext().setAttribute(REGISTRY_CONTEXT_NAME,registry);
        registry.performRegistryStartup();
    }
    /**
     * Overridden in subclasses to provide additional module definitions beyond those normally
     * located. This
     * implementation returns an empty array.
     */
    protected ModuleDef[] provideExtraModuleDefs(ServletContext context)
    {
        return new ModuleDef[0];
    }

    /**
     * Overriden in subclasses to provide additional module classes beyond those normally located. This implementation
     * returns an empty array.
     *
     * @since 5.3
     */
    protected Class[] provideExtraModuleClasses(ServletContext context)
    {
        return new Class[0];
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        registry.shutdown();
    }
}
