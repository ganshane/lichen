// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.testdb.index;

import lichen.migration.model.SqlType;
import lichen.migration.model.TableDefinition;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;
import lichen.migration.services.TableCallback;

import javax.inject.Inject;

/**
 * @author jcai
 */
public class Migrate_20130804213344_CreateTable implements Migration{
    @Inject
    private MigrationHelper helper;
    @Inject
    private Options options;

    @Override
    public void up() throws Throwable {
        helper.createTable("test_table", new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.varchar("field1");
                t.varchar("field2");
                t.varchar("field3");
                t.varchar("field4");
                t.varchar("field5");
                t.varchar("field6");
                t.varchar("field7");
            }
        });
    }

    @Override
    public void down() throws Throwable {
        helper.dropTable("test_table");
    }
}
