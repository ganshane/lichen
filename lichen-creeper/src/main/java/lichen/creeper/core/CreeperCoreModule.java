package lichen.creeper.core;

import lichen.core.services.LichenException;
import lichen.core.services.Option;
import lichen.creeper.core.annotations.CreeperCore;
import lichen.creeper.core.config.CreeperCoreConfig;
import lichen.creeper.core.internal.CreeperModuleManagerImpl;
import lichen.creeper.core.internal.H2ConsoleRunner;
import lichen.creeper.core.internal.InitializeObjectWorker;
import lichen.creeper.core.internal.MenuSourceImpl;
import lichen.creeper.core.internal.jpa.OpenEntityManagerInViewFilter;
import lichen.creeper.core.internal.override.CreeperOverrideModule;
import lichen.creeper.core.models.CreeperMenu;
import lichen.creeper.core.services.CreeperCoreExceptionCode;
import lichen.creeper.core.services.CreeperModuleManager;
import lichen.creeper.core.services.MenuSource;
import lichen.creeper.core.services.XmlLoader;
import lichen.creeper.core.services.db.DatabaseMigrationModule;
import lichen.creeper.core.services.jpa.CreeperJpaModule;
import lichen.creeper.core.services.jpa.CreeperJpaValueEncoderSourceModule;
import lichen.creeper.core.services.shiro.CreeperShiroModule;
import lichen.creeper.user.UserModule;
import org.apache.commons.io.FileUtils;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.func.Worker;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.*;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Creeper的核心入口类
 */
@SubModule({DatabaseMigrationModule.class,CreeperJpaModule.class,
        CreeperShiroModule.class,UserModule.class,
        CreeperOverrideModule.class, CreeperJpaValueEncoderSourceModule.class
})
public class CreeperCoreModule {
    @SuppressWarnings("unchecked")
	public static void bind(ServiceBinder binder){
        binder.bind(MenuSource.class, MenuSourceImpl.class).withMarker(CreeperCore.class);
        binder.bind(CreeperModuleManager.class, CreeperModuleManagerImpl.class).withMarker(CreeperCore.class);
    }

    /**
     * 在开发模式下，自动启动数据库管理工具
     */
    @Startup
    public static void startupH2Console(
            @Symbol(SymbolConstants.PRODUCTION_MODE) boolean isProduction, final CreeperCoreConfig config,
            final DataSource dataSource
            ){
        if(!isProduction){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        H2ConsoleRunner.run(dataSource.getConnection());
                    } catch (SQLException e) {
                        throw LichenException.wrap(e);
                    }
                }
            }).start();
        }
    }

	@SuppressWarnings("deprecation")
	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration){
		configuration.add(SymbolConstants.SUPPRESS_REDIRECT_FROM_ACTION_REQUESTS, "true");
        configuration.add(SymbolConstants.SECURE_ENABLED,"false");
	}
    
    /**
     * Contribution to the
     * {@link org.apache.tapestry5.services.ComponentClassResolver} service
     * configuration.
     */
    public static void contributeComponentClassResolver(
            final Configuration<LibraryMapping> configuration,@CreeperCore CreeperModuleManager creeperModuleManager) {
        configuration.add(new LibraryMapping("creeper", "lichen.creeper.core"));
        //自动加载各个模块的页面类
        creeperModuleManager.flowModuleSubPackageWithSuffix(Option.none(String.class)).each(new Worker<String>() {
            @Override
            public void work(String element) {
                String moduleName = element.substring(element.lastIndexOf(".")+1);
                configuration.add(new LibraryMapping(moduleName, element));
            }
        });
    }
    @Marker(CreeperCore.class)
    public static CreeperCoreConfig buildCreeperCoreConfig(@Symbol(CreeperCoreSymbols.SERVER_HOME) String serverHome){
        String filePath = serverHome + "/config/creeper-core.xml";
        try {
            FileInputStream content = FileUtils.openInputStream(new File(filePath));
            return XmlLoader.parseXML(CreeperCoreConfig.class,content, Option.some(CreeperCoreModule.class.getResourceAsStream("/lichen/creeper/core/config/CreeperCoreConfig.xsd")));
        } catch (IOException e) {
            LichenException le = LichenException.wrap(e, CreeperCoreExceptionCode.FAIL_READ_CONFIG_FILE);
            le.set("file",filePath);
            throw le;
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
            private LichenException findLichenException(Throwable exception){
                if(exception ==null || exception instanceof LichenException)
                    return (LichenException) exception;
                return findLichenException(exception.getCause());
            }
            public void handleRequestException(Throwable exception) throws IOException
            {
                LichenException LichenException = findLichenException(exception);
                if(LichenException != null){
                    logger.error(LichenException.getMessage(), LichenException);
                    ExceptionReporter exceptionPage = (ExceptionReporter) componentSource.getPage("ExceptionPage");
                    exceptionPage.reportException(LichenException);

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
    @Contribute(MenuSource.class)
    public static void provideMenu(Configuration<CreeperMenu> configuration){
        configuration.add(new CreeperMenu("admin", "管理", "/admin", 1, CreeperMenu.MENU_VIRTUAL));
    }
    @Contribute(TypeCoercer.class)
    public static void provideTypeCoercer(Configuration<CoercionTuple> configuration){
        configuration.add(CoercionTuple.create(UUID.class,String.class,new Coercion<UUID, String>() {
            @Override
            public String coerce(UUID input) {
                return input.toString();
            }
        }));
        configuration.add(CoercionTuple.create(String.class, UUID.class,new Coercion<String, UUID>() {
            @Override
            public UUID coerce(String input) {
                return UUID.fromString(input);
            }
        }));
        configuration.add(CoercionTuple.create(Page.class, Iterable.class, new Coercion<Page,Iterable>(){
			@Override
			public Iterable coerce(Page input) {
				return input.getContent();
			}}));
    }
    
    @Contribute(ComponentClassTransformWorker2.class)
    public static void provideComponentClassTransformWorkers(OrderedConfiguration<ComponentClassTransformWorker2> configuration){
    	configuration.addInstance("InitializeObject", InitializeObjectWorker.class);
    }
}
