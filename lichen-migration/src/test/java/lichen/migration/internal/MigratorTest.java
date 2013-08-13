// Copyright 2013 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package lichen.migration.internal;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * migrator test
 * @author jcai
 */
public class MigratorTest {
    private static Migrator migrator;

    @Test
    public void test_migrate() throws Throwable {
        migrator.migrate(MigratorOperation.InstallAllMigrations,"lichen.migration.testdb", false);
        migrator.migrate(MigratorOperation.RemoveAllMigrations,"lichen.migration.testdb", false);
    }
    
    //创建视图测试用例
    @Test
    public void test_migrate_index() throws Throwable {
        migrator.migrate(MigratorOperation.InstallAllMigrations,"lichen.migration.testdb.index", false);
        migrator.migrate(MigratorOperation.RemoveAllMigrations,"lichen.migration.testdb.index", false);
    }
    
    @BeforeClass
    public static void setup() throws ProxoolException {
        String driver_class_name = "org.h2.Driver";
        DatabaseVendor vendor = DatabaseVendor.forDriver(driver_class_name);
        DatabaseAdapter databaseAdapter = DatabaseAdapter.forVendor(vendor, Option.<String>None());
        Properties info = new Properties();
        info.setProperty("jdbc-x.proxool.alias", "test");
        info.setProperty("jdbc-x.proxool.maximum-connection-count", "50");
        info.setProperty("jdbc-x.user", "sa");
        info.setProperty("jdbc-x.proxool.driver-class", "org.h2.Driver");
        info.setProperty("jdbc-x.proxool.driver-url", "jdbc:h2:mem:testdb");

        info.setProperty("jdbc-x.proxool.maximum-connection-lifetime", "18000000000");
        info.setProperty("jdbc-x.proxool.maximum-active-time", "60000000000");

        //configuration proxool database source
        PropertyConfigurator.configure(info);
        //new datasource
        DataSource dataSource = new ProxoolDataSource("test");

        migrator = new Migrator(dataSource, databaseAdapter);
    }
    @AfterClass
    public static void shutdown(){
        ProxoolFacade.shutdown();
    }
}
