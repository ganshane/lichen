package lichen.creeper.user.db;

import javax.inject.Inject;

import lichen.migration.model.TableDefinition;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;
import lichen.migration.services.TableCallback;

/**
 * @author jcai
 */
public class Migrate_20140617163012_CreateRole implements Migration{
    @Inject
    private MigrationHelper _helper;
    @Inject
    private Options _options;

    @Override
    public void up() throws Throwable {
        _helper.createTable("roles",new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.varbinary("id",_options.PrimaryKey(),_options.Limit(16));
                t.varchar("name",_options.NotNull(),_options.Limit(64));
            }
        });
        _helper.commentTable("roles", "角色");
        _helper.commentColumn("roles", "id", "主键");
        _helper.commentColumn("roles","name","角色名称");

        _helper.createTable("permissions",new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.varbinary("id",_options.PrimaryKey(),_options.Limit(16));
                t.varbinary("role_id",_options.NotNull(),_options.Limit(16));
                t.varchar("permission",_options.Limit(200));
            }
        });
        _helper.commentTable("permissions", "许可");
        _helper.commentColumn("permissions", "id", "主键");
        _helper.commentColumn("permissions", "role_id", "关联角色");
        _helper.commentColumn("permissions","permission","许可");

        _helper.addIndex("permissions","role_id");

        _helper.createTable("users_roles",new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.varbinary("user_id",_options.NotNull(),_options.Limit(16));
                t.varbinary("role_id",_options.NotNull(),_options.Limit(16));
            }
        });
        _helper.addIndex("users_roles","user_id");
        _helper.addIndex("users_roles","role_id");
    }

    @Override
    public void down() throws Throwable {
        _helper.dropTable("roles");
        _helper.dropTable("permissions");
    }
}
