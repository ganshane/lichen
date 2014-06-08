package creeper.core.services.db;

import creeper.core.internal.DatabaseMigrationImpl;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.slf4j.Logger;

/**
 * 数据库升级的模块
 * @author jcai
 */
public class DatabaseMigrationModule {
    public static void bind(ServiceBinder binder){
        binder.bind(DatabaseMigration.class,DatabaseMigrationImpl.class);
    }
    @Startup
    public static void initCreeperDatabase(Logger logger, DatabaseMigration service){
        logger.info("upgrading database schema ...");
        service.dbSetup();
        logger.info("database upgraded.");
    }
}
