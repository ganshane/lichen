package creeper.core.internal;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;

import creeper.core.annotations.CreeperCore;
import creeper.core.services.CreeperModuleManager;
import lichen.migration.internal.DatabaseAdapter;
import lichen.migration.internal.DatabaseVendor;
import lichen.migration.internal.Migrator;
import lichen.migration.internal.MigratorOperation;
import lichen.migration.internal.Option;

import org.apache.tapestry5.func.Predicate;

import creeper.core.config.CreeperCoreConfig;
import creeper.core.services.CreeperCoreExceptionCode;
import creeper.core.services.CreeperException;
import creeper.core.services.db.DatabaseMigration;

import javax.sql.DataSource;

public class DatabaseMigrationImpl implements DatabaseMigration {
	

    private final CreeperModuleManager _creeperModuleManager;
    private final DataSource _dataSource;
    private final Migrator migrator;

    public DatabaseMigrationImpl(@CreeperCore CreeperModuleManager creeperModuleManager,
                                 CreeperCoreConfig creeperCoreConfig,
                                 DataSource dataSource){
        _creeperModuleManager = creeperModuleManager;
        _dataSource = dataSource;
        //Oracle的schema必须和数据库用户名一致。
        DatabaseVendor vendor = DatabaseVendor.forDriver(creeperCoreConfig.db.driverClassName);
        DatabaseAdapter databaseAdapter = DatabaseAdapter.forVendor(vendor, Option.some(creeperCoreConfig.db.username));
        migrator = new Migrator(_dataSource, databaseAdapter);
	}

	@Override
	public void dbSetup() {
        Iterator<String> itor = _creeperModuleManager.flowModuleSubPackageWithSuffix(lichen.core.services.Option.some("db")).
                filter(new Predicate<String>() {
                    @Override
                    public boolean accept(String packageName) {
                        String pn = packageName.replace('.', '/');

                        try {
                            Enumeration<URL> urls = DatabaseMigrationImpl.class.
                                    getClassLoader().getResources(pn);
                            return urls.hasMoreElements();
                        } catch (IOException e) {
                            throw CreeperException.wrap(e);
                        }
                    }
                }).iterator();

            while(itor.hasNext())
            {
                String packageName = itor.next();
                try {
                    migrator.migrate(MigratorOperation.InstallAllMigrations, packageName, false);
                } catch (Throwable e) {
                    CreeperException ce = CreeperException.wrap(e, CreeperCoreExceptionCode.FAIL_MIGRAT_SCRIPT);
                    ce.set("script_package", packageName);
                    throw ce;
                }
            }
        }

    }
