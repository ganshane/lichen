package lichen.migration.internal;

import org.junit.Test;

/**
 * migrator test
 * @author jcai
 */
public class MigratorTest {
    @Test
    public void test_migrate() throws Throwable {
        String driver_class_name = "org.h2.Driver";
        DatabaseVendor vendor = DatabaseVendor.forDriver(driver_class_name);
        DatabaseAdapter databaseAdapter = DatabaseAdapter.forVendor(vendor, Option.<String>None());
        Migrator migrator = new Migrator("jdbc:h2:mem:target/testdb", "sa", "", databaseAdapter);
        migrator.migrate(MigratorOperation.InstallAllMigrations,"com.imageworks.vnp.dao.migrations", false);
    }
}
