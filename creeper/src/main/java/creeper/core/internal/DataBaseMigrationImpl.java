package creeper.core.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.sql.DataSource;

import lichen.migration.internal.DatabaseAdapter;
import lichen.migration.internal.DatabaseVendor;
import lichen.migration.internal.Migrator;
import lichen.migration.internal.MigratorOperation;
import lichen.migration.internal.Option;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;

import creeper.core.config.CreeperCoreConfig;
import creeper.core.models.CreeperDataBaseMigrationScript;
import creeper.core.services.CreeperCoreExceptionCode;
import creeper.core.services.CreeperException;
import creeper.core.services.DataBaseMigrationService;

public class DataBaseMigrationImpl implements DataBaseMigrationService {
	
	private Collection<CreeperDataBaseMigrationScript> _coll;
	
	private static Migrator migrator;
	
	@Inject
	private CreeperCoreConfig creeperCoreConfig;
	
	public DataBaseMigrationImpl(Collection<CreeperDataBaseMigrationScript> coll,CreeperCoreConfig creeperCoreConfig){
		_coll = coll;
		this.creeperCoreConfig = creeperCoreConfig;
		DatabaseVendor vendor = DatabaseVendor.forDriver(this.creeperCoreConfig.db._driverClassName);
        //Oracle的schema必须和数据库用户名一致。
        DatabaseAdapter databaseAdapter = DatabaseAdapter.forVendor(vendor, Option.some(this.creeperCoreConfig.db._username));
        Properties info = new Properties();
        info.setProperty("jdbc-x.proxool.alias", "monad");
        info.setProperty("jdbc-x.proxool.maximum-connection-count", "50");
        info.setProperty("jdbc-x.user", this.creeperCoreConfig.db._username);
        info.setProperty("jdbc-x.password", this.creeperCoreConfig.db._password);
        info.setProperty("jdbc-x.proxool.driver-class", this.creeperCoreConfig.db._driverClassName);
        info.setProperty("jdbc-x.proxool.driver-url", this.creeperCoreConfig.db._url);

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
        DataSource dataSource = new ProxoolDataSource("monad");
        migrator = new Migrator(dataSource, databaseAdapter);
	}

	@Override
	public void dbSetup() {
		Iterator<CreeperDataBaseMigrationScript> itor = _coll.iterator();
		while(itor.hasNext()){
			CreeperDataBaseMigrationScript script = itor.next();
			try {
				migrator.migrate(MigratorOperation.InstallAllMigrations, script.getPackageName(), script.isSearchSubPackages());
			} catch (Throwable e) {
				CreeperException ce = CreeperException.wrap(e, CreeperCoreExceptionCode.FAIL_MIGRAT_SCRIPT);
	            ce.set("script_package",script.getPackageName());
	            throw ce;
			}
		}
	}

}
