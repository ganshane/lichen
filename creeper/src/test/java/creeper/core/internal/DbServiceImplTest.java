package creeper.core.internal;

import java.util.ArrayList;
import java.util.List;

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
        List<CreeperDatabaseMigrationScript> coll= new ArrayList<CreeperDatabaseMigrationScript>();
        coll.add(new CreeperDatabaseMigrationScript("creeper.core.testdb",false));

        CreeperCoreConfig conf = new CreeperCoreConfig();
        conf.db._driverClassName = "org.h2.Driver";
        conf.db._url = "jdbc:h2:mem:testdb";
        conf.db._username = "sa";
        conf.db._password = "";
        
        DatabaseMigrationImpl dbservice = new DatabaseMigrationImpl(coll, conf);
        dbservice.dbSetup();
    }
}
