package creeper.core.internal;

import java.util.Arrays;
import java.util.Properties;

import creeper.core.models.CreeperModuleDef;
import creeper.core.services.CreeperCoreExceptionCode;
import creeper.core.services.CreeperException;
import creeper.core.services.CreeperModuleManager;
import org.junit.Test;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import creeper.core.config.CreeperCoreConfig;
import org.springframework.util.StringUtils;

/**
 * 
 * @author shen
 *
 */
public class DatabaseMigrationImplTest {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigrationImplTest.class);
    
    @Test
    public void testDbSetup() throws ProxoolException {
        CreeperModuleManager manager = new CreeperModuleManagerImpl(Arrays.asList(CreeperModuleDef.create("测试","creeper.test")));

        CreeperCoreConfig creeperCoreConfig = new CreeperCoreConfig();
        creeperCoreConfig.db._driverClassName = "org.h2.Driver";
        creeperCoreConfig.db._url = "jdbc:h2:mem:testdb";
        creeperCoreConfig.db._username = "sa";
        creeperCoreConfig.db._password = "";

        Properties info = new Properties();
        info.setProperty("jdbc-x.proxool.alias", "creeper-migrator");
        info.setProperty("jdbc-x.proxool.maximum-connection-count", "50");
        info.setProperty("jdbc-x.user", creeperCoreConfig.db._username);
        if(StringUtils.hasText(creeperCoreConfig.db._password))
            info.setProperty("jdbc-x.password", creeperCoreConfig.db._password);
        info.setProperty("jdbc-x.proxool.driver-class", creeperCoreConfig.db._driverClassName);
        info.setProperty("jdbc-x.proxool.driver-url", creeperCoreConfig.db._url);

        info.setProperty("jdbc-x.proxool.maximum-connection-lifetime", "18000000000");
        info.setProperty("jdbc-x.proxool.maximum-active-time", "60000000000");

        //configuration proxool database source
        try {
            PropertyConfigurator.configure(info);
        } catch (ProxoolException e) {
            CreeperException ce = CreeperException.wrap(e, CreeperCoreExceptionCode.FAIL_CONFIG_PROXOOL);
            throw ce;
        }
        //new datasource
        ProxoolDataSource _dataSource = new ProxoolDataSource("creeper-migrator");
        
        DatabaseMigrationImpl dbservice = new DatabaseMigrationImpl(manager, creeperCoreConfig,_dataSource);
        dbservice.dbSetup();
        ProxoolFacade.removeConnectionPool("creeper-migrator");
    }
}
