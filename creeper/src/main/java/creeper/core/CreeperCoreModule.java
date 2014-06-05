package creeper.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import lichen.migration.internal.Option;

import org.apache.commons.io.FileUtils;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.LibraryMapping;
import org.slf4j.Logger;

import creeper.core.config.CreeperCoreConfig;
import creeper.core.internal.DataBaseMigrationImpl;
import creeper.core.internal.MenuSourceImpl;
import creeper.core.services.CreeperCoreExceptionCode;
import creeper.core.services.CreeperException;
import creeper.core.services.DataBaseMigrationService;
import creeper.core.services.MenuSource;
import creeper.core.services.XmlLoader;

public class CreeperCoreModule {
    public static void bind(ServiceBinder binder){
        binder.bind(MenuSource.class,MenuSourceImpl.class);
        binder.bind(DataBaseMigrationService.class,DataBaseMigrationImpl.class);
    }
    /**
     * Contribution to the
     * {@link org.apache.tapestry5.services.ComponentClassResolver} service
     * configuration.
     */
    public static void contributeComponentClassResolver(
            Configuration<LibraryMapping> configuration) {
        configuration.add(new LibraryMapping("creeper","creeper.core"));
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
    
    @Startup  
    public static void initMyApplication(Logger logger, DataBaseMigrationService service){    
    	logger.info("Starting up...");     
    	service.dbSetup();
    }
}
