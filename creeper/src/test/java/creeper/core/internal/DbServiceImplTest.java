package creeper.core.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import creeper.core.services.CreeperModuleManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import creeper.core.config.CreeperCoreConfig;
import creeper.core.models.CreeperDatabaseMigrationScript;

/**
 * 
 * @author shen
 *
 */
public class DbServiceImplTest {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceImplTest.class);
    
    @Test
    public void testDbSetup(){
        CreeperModuleManager manager = new CreeperModuleManagerImpl(Arrays.asList("creeper.test"));

        CreeperCoreConfig conf = new CreeperCoreConfig();
        conf.db._driverClassName = "org.h2.Driver";
        conf.db._url = "jdbc:h2:mem:testdb";
        conf.db._username = "sa";
        conf.db._password = "";
        
        DatabaseMigrationImpl dbservice = new DatabaseMigrationImpl(manager, conf);
        dbservice.dbSetup();
    }
}
