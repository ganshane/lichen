package creeper.user.db;

import javax.inject.Inject;

import lichen.migration.model.TableDefinition;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;
import lichen.migration.services.TableCallback;

/**
 * 
 * @author shen
 *
 */
public class Migrate_20140606010101_CreateUsers implements Migration {
    @Inject
    private MigrationHelper _helper;
    @Inject
    private Options _options;

    @Override
    public void up() throws Throwable {
        _helper.createTable("users",new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.integer("id", _options.PrimaryKey());
                t.varchar("name", _options.NotNull());
                t.varchar("username", _options.NotNull());
                t.varchar("password", _options.NotNull());
            }
        });

        _helper.commentTable("users", _options.Comment("用户信息"));
        _helper.commentColumn("users", "id", _options.Comment("主键"));
        _helper.commentColumn("users", "name", _options.Comment("姓名"));
        _helper.commentColumn("users", "username", _options.Comment("用户名"));
        _helper.commentColumn("users", "password", _options.Comment("密码"));
        
        _helper.createSequence("user_seq", _options.Start(1), _options.MinValue(1),_options.Increment(1));

    }

    @Override
    public void down() throws Throwable {
        _helper.dropTable("users");
        _helper.dropSequence("user_seq");
    }
}
