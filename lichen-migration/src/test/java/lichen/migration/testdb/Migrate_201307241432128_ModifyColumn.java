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
public class Migrate_201307241432128_ModifyColumn implements Migration{
    @Inject
    private MigrationHelper helper;
    @Inject
    private Options options;

    @Override
    public void up() throws Throwable {
        helper.alterColumn("test_table","test_col",
                SqlType.DecimalType,
                options.NotNull(),
                options.Precision(10),
                options.Scale(2));
    }

    @Override
    public void down() throws Throwable {
        helper.alterColumn("test_table","test_col",SqlType.VarcharType,options.NotNull());
    }
}
