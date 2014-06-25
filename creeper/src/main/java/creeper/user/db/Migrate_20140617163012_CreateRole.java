package creeper.user.db;

import lichen.migration.model.TableDefinition;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;
import lichen.migration.services.TableCallback;

import javax.inject.Inject;

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
                t.varchar("id",_options.PrimaryKey(),_options.Limit(32));
                t.varchar("name",_options.NotNull(),_options.Limit(64));
            }
        });
        _helper.commentTable("roles", _options.Comment("角色"));
        _helper.commentColumn("roles", "id", _options.Comment("主键"));
        _helper.commentColumn("roles","name",_options.Comment("角色名称"));

        _helper.createTable("permissions",new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.varchar("id",_options.PrimaryKey(),_options.Limit(32));
                t.varchar("role_id",_options.NotNull(),_options.Limit(32));
                t.varchar("permission",_options.Limit(200));
            }
        });
        _helper.commentTable("permissions", _options.Comment("许可"));
        _helper.commentColumn("permissions", "id", _options.Comment("主键"));
        _helper.commentColumn("permissions", "role_id", _options.Comment("关联角色"));
        _helper.commentColumn("permissions","permission",_options.Comment("许可"));

        _helper.addIndex("permissions","role_id");

        _helper.createTable("users_roles",new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.varchar("user_id",_options.NotNull(),_options.Limit(32));
                t.varchar("role_id",_options.NotNull(),_options.Limit(32));
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
