package lichen.creeper.core.services.db;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.slf4j.Logger;

import lichen.creeper.core.internal.DatabaseMigrationImpl;

/**
 * 数据库升级的模块
 * @author jcai
 */
public class DatabaseMigrationModule {
    public static void bind(ServiceBinder binder){
        binder.bind(DatabaseMigration.class,DatabaseMigrationImpl.class);
    }

    @Contribute(Runnable.class)
    public static void initCreeperDatabase(final Logger logger, final DatabaseMigration service,OrderedConfiguration<Runnable> configuration){
        configuration.add("database-migration", new Runnable() {
            @Override
            public void run() {
                logger.info("upgrading database schema ...");
                service.dbSetup();
                logger.info("database upgraded.");
            }
        });
    }
}
