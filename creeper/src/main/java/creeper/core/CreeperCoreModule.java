package creeper.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;

import lichen.core.services.Option;

import org.apache.commons.io.FileUtils;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.func.Worker;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ClassNameLocator;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.ExceptionReporter;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.ResponseRenderer;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.got5.tapestry5.jquery.JQuerySymbolConstants;
import org.slf4j.Logger;

import creeper.core.annotations.CreeperCore;
import creeper.core.config.CreeperCoreConfig;
import creeper.core.internal.CreeperModuleManagerImpl;
import creeper.core.internal.EntityValueEncoder;
import creeper.core.internal.MenuSourceImpl;
import creeper.core.internal.jpa.OpenEntityManagerInViewFilter;
import creeper.core.services.CreeperCoreExceptionCode;
import creeper.core.services.CreeperException;
import creeper.core.services.CreeperModuleManager;
import creeper.core.services.MenuSource;
import creeper.core.services.XmlLoader;
import creeper.core.services.db.DatabaseMigrationModule;
import creeper.core.services.jpa.CreeperJpaModule;
import creeper.core.services.shiro.CreeperShiroModule;
import creeper.user.UserModule;

@SubModule({DatabaseMigrationModule.class,CreeperJpaModule.class, CreeperShiroModule.class,UserModule.class})
public class CreeperCoreModule {
    public static void bind(ServiceBinder binder){
        binder.bind(MenuSource.class, MenuSourceImpl.class);
        binder.bind(CreeperModuleManager.class, CreeperModuleManagerImpl.class);
    }
    
	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration){
		configuration.add(JQuerySymbolConstants.SUPPRESS_PROTOTYPE, "true");
	}
    
    /**
     * Contribution to the
     * {@link org.apache.tapestry5.services.ComponentClassResolver} service
     * configuration.
     */
    public static void contributeComponentClassResolver(
            final Configuration<LibraryMapping> configuration,@CreeperCore CreeperModuleManager creeperModuleManager) {
        configuration.add(new LibraryMapping("creeper", "creeper.core"));
        //自动加载各个模块的页面类
        creeperModuleManager.flowModuleSubPackageWithSuffix(Option.none(String.class)).each(new Worker<String>() {
            @Override
            public void work(String element) {
                String moduleName = element.substring(element.lastIndexOf(".")+1);
                configuration.add(new LibraryMapping(moduleName, element));
            }
        });
    }
    public static CreeperCoreConfig buildCreeperCoreConfig(@Symbol(CreeperCoreSymbols.SERVER_HOME) String serverHome){
        String filePath = serverHome + "/config/creeper-core.xml";
        try {
            FileInputStream content = FileUtils.openInputStream(new File(filePath));
            return XmlLoader.parseXML(CreeperCoreConfig.class,content, Option.some(CreeperCoreModule.class.getResourceAsStream("/creeper/core/config/CreeperCoreConfig.xsd")));
        } catch (IOException e) {
            CreeperException ce = CreeperException.wrap(e, CreeperCoreExceptionCode.FAIL_READ_CONFIG_FILE);
            ce.set("file",filePath);
            throw ce;
        }
    }
    public RequestExceptionHandler decorateRequestExceptionHandler(
            final Logger logger,
            final RequestExceptionHandler delegate,
            final ResponseRenderer renderer,
            final ComponentSource componentSource,
            @Symbol(SymbolConstants.PRODUCTION_MODE)
            boolean productionMode,
            Object service)
    {
        return new RequestExceptionHandler()
        {
            private CreeperException findCreeperException(Throwable exception){
                if(exception ==null || exception instanceof CreeperException)
                    return (CreeperException) exception;
                return findCreeperException(exception.getCause());
            }
            public void handleRequestException(Throwable exception) throws IOException
            {
                CreeperException creeperException = findCreeperException(exception);
                if(creeperException != null){
                    logger.error(creeperException.getMessage(), creeperException);
                    ExceptionReporter exceptionPage = (ExceptionReporter) componentSource.getPage("ExceptionPage");
                    exceptionPage.reportException(creeperException);

                    renderer.renderPageMarkupResponse("ExceptionPage");
                }else{
                    delegate.handleRequestException(exception);
                }
            }
        };
    }
    @Contribute(RequestHandler.class)
    public static void provideOpenEntityManagerInView(OrderedConfiguration<RequestFilter> configuration)
    {
        configuration.addInstance("OpenEntityManagerInViewFilter", OpenEntityManagerInViewFilter.class, "after:StaticFiles");
    }
    
	@SuppressWarnings("unchecked")
	@Contribute(ValueEncoderSource.class)
	public static void contributeValueEncoderSource(Logger logger,
			MappedConfiguration<Class<?>, Object> configuration,
			final CreeperModuleManager creeperModuleManager,
			final PropertyAccess propertyAccess, final TypeCoercer typeCoercer,final ClassNameLocator classNameLocator,EntityManager entityManager) {
    	
    	String[] entitiesPackageNames = creeperModuleManager.getModuleSubPackageWithSuffix(Option.some("entities"));
    	for(String entitiesPackage : entitiesPackageNames){
    		for(String className : classNameLocator.locateClassNames(entitiesPackage)){
    			try {
					Class<?> clazz = Class.forName(className);
					if(null != clazz.getAnnotation(Entity.class)){
						Field[] fields = clazz.getDeclaredFields();
						for(Field field : fields){
							if(null != field.getAnnotation(Id.class)){
								configuration.add(clazz, new EntityValueEncoder(
										clazz, field.getName() , propertyAccess,
										typeCoercer, entityManager));
								break;
							}
						}
					}
				} catch (ClassNotFoundException e) {
					throw CreeperException.wrap(e);
				}
    		}
    	}
	}

}
