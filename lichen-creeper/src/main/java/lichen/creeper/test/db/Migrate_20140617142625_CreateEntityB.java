package lichen.creeper.test.db;

import lichen.migration.model.TableDefinition;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;
import lichen.migration.services.TableCallback;

import javax.inject.Inject;

/**
 * @author jcai
 */
public class Migrate_20140617142625_CreateEntityB implements Migration{

    @Inject
    private MigrationHelper _helper;
    @Inject
    private Options _options;
    @Override
    public void up() throws Throwable {
        _helper.createTable("entity_b",new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.varchar("id", _options.PrimaryKey());
                t.varchar("name");
                t.integer("date");
            }
        });
    }

    @Override
    public void down() throws Throwable {
        _helper.dropTable("entity_b");
    }
}
