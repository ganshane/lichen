package lichen.migration.internal;

import lichen.migration.model.TableDefinition;
import lichen.migration.services.*;

import javax.inject.Inject;

/**
 * 创建数据库初始化脚本
 * @author jcai
 */
public class CreateSchemaMigrationsTableMigration  implements Migration {
    @Inject
    private MigrationHelper helper;
    @Inject
    private Options options;

    public void up() throws Throwable {
        helper.createTable(Migrator.schemaMigrationsTableName, new TableCallback() {
            public void doInTable(TableDefinition t) throws Throwable {
                t.varchar("version", options.Limit(32), options.NotNull());
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
