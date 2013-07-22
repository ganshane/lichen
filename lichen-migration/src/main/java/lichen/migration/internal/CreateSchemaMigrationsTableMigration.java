package lichen.migration.internal;

import lichen.migration.model.TableDefinition;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;
import lichen.migration.services.TableCallback;

import javax.inject.Inject;

/**
 * 创建数据库初始化脚本
 * @author jcai
 */
public class CreateSchemaMigrationsTableMigration  implements Migration {
    @Inject
    private MigrationHelper helper;

    public void up() throws Throwable {
        helper.createTable(Migrator.schemaMigrationsTableName, new TableCallback() {
            public void doInTable(TableDefinition t) throws Throwable {
                t.varchar("version", Options.Limit(32), Options.NotNull);
            }
        });

        /*
        addIndex(Migrator.schemaMigrationsTableName,
                Array("version"),
                Unique,
                Name("unique_schema_migrations"))
        */
    }

    public void down() {
        throw new IllegalStateException("Fail to down");
    }
}
