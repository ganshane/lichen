package lichen.creeper.core;

import lichen.creeper.core.annotations.CreeperCore;
import lichen.creeper.core.config.CreeperCoreConfig;
import lichen.creeper.core.internal.CreeperModuleManagerImpl;
import lichen.creeper.core.internal.H2ConsoleRunner;
import lichen.creeper.core.internal.MenuSourceImpl;
import lichen.creeper.core.internal.jpa.OpenEntityManagerInViewFilter;
import lichen.creeper.core.internal.override.CreeperOverrideModule;
import lichen.creeper.core.models.CreeperMenu;
import lichen.creeper.core.services.*;
import lichen.creeper.core.services.db.DatabaseMigrationModule;
import lichen.creeper.core.services.jpa.CreeperJpaModule;
import lichen.creeper.core.services.jpa.CreeperJpaValueEncoderSourceModule;
import lichen.creeper.core.services.shiro.CreeperShiroModule;
import lichen.creeper.user.UserModule;
import lichen.core.services.Option;
import org.apache.commons.io.FileUtils;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.func.Worker;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.services.*;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

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
                        throw CreeperException.wrap(e);
                    }
                }
            }).start();
        }
    }

	@SuppressWarnings("deprecation")
	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration){
		configuration.add(SymbolConstants.SUPPRESS_REDIRECT_FROM_ACTION_REQUESTS, "true");
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
    @Contribute(MenuSource.class)
    public static void provideMenu(Configuration<CreeperMenu> configuration){
        configuration.add(new CreeperMenu("admin", "管理", "/admin", 1, CreeperMenu.MENU_VIRTUAL));
    }
}
