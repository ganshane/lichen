package creeper.core.internal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import creeper.core.config.CreeperCoreConfig;
import creeper.core.models.CreeperDbScript;

/**
 * 
 * @author shen
 *
 */
public class DbServiceImplTest {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceImplTest.class);
    
    @Test
    public void testDbSetup(){
        List<CreeperDbScript> coll= new ArrayList<CreeperDbScript>();
        coll.add(new CreeperDbScript("creeper.core.testdb",false));

        CreeperCoreConfig conf = new CreeperCoreConfig();
        conf.db._driverClassName = "org.h2.Driver";
        conf.db._url = "jdbc:h2:mem:testdb";
        conf.db._username = "sa";
        conf.db._password = "";
        
        DataBaseMigrationImpl dbservice = new DataBaseMigrationImpl(coll, conf);
        boolean status = dbservice.dbSetup();
        logger.debug("db升级:{}",status);
    }
}
