package lichen.creeper.core.services.db;

import lichen.creeper.core.internal.DatabaseMigrationImpl;
import org.apache.tapestry5.ioc.ServiceBinder;

/**
 * 数据库升级的模块
 * @author jcai
 */
public class DatabaseMigrationModule {
    public static void bind(ServiceBinder binder){
        binder.bind(DatabaseMigration.class,DatabaseMigrationImpl.class).eagerLoad();
    }
}
