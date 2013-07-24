// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.testdb;

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
public class Migrate_20130722093444_CreateTable implements Migration{
    @Inject
    private MigrationHelper helper;
    @Inject
    private Options options;

    @Override
    public void up() throws Throwable {
        helper.createTable("test_table",new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.bigint("bigint");
                t.blob("blob");
                t.bool("bool");
                t.char_("char_");
                t.column("column_", SqlType.BigintType);
                //t.decimal("decimal_",Op);
                t.integer("int_",options.Unique());
                t.smallint("sint_");
                t.timestamp("ts");
                t.varbinary("vb");
                t.varchar("vc",options.Limit(10),options.Default("'asdf'"));
            }
        });

        helper.addColumn("test_table","test_col",SqlType.VarcharType,options.NotNull());

    }

    @Override
    public void down() throws Throwable {
        helper.removeColumn("test_table","test_col");
        helper.dropTable("test_table");
    }
}
