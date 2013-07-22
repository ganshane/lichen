package lichen.migration.testdb;

import lichen.migration.model.SqlType;
import lichen.migration.model.TableDefinition;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.TableCallback;

import javax.inject.Inject;

/**
 * @author jcai
 */
public class Migrate_20130722093444_CreateTable implements Migration{
    @Inject
    private MigrationHelper helper;

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
                t.integer("int_");
                t.smallint("sint_");
                t.timestamp("ts");
                t.varbinary("vb");
                t.varchar("vc");
            }
        });
    }

    @Override
    public void down() throws Throwable {
        helper.dropTable("test_table");
    }
}
