package creeper.core;

import creeper.core.annotations.CreeperCore;
import creeper.core.config.CreeperCoreConfig;
import creeper.core.internal.CreeperModuleManagerImpl;
import creeper.core.internal.DatabaseMigrationImpl;
import creeper.core.internal.MenuSourceImpl;
import creeper.core.services.*;
import creeper.core.services.jpa.CreeperJpaModule;
import creeper.core.services.shiro.CreeperShiroModule;
import lichen.core.services.Option;
import org.apache.commons.io.FileUtils;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.func.Worker;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.*;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SubModule({CreeperJpaModule.class, CreeperShiroModule.class})
public class CreeperCoreModule {
    public static void bind(ServiceBinder binder){
        binder.bind(MenuSource.class, MenuSourceImpl.class);
        binder.bind(DatabaseMigration.class,DatabaseMigrationImpl.class);
        binder.bind(CreeperModuleManager.class, CreeperModuleManagerImpl.class);
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

    @Startup
    public static void initCreeperDatabase(Logger logger, DatabaseMigration service){
        logger.info("upgrading database schema ...");
        service.dbSetup();
        logger.info("database upgraded.");
    }
}
