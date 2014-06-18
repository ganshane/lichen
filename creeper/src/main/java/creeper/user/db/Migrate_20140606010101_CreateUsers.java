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
                t.varchar("id", _options.PrimaryKey(),_options.Limit(32));
                t.varchar("name", _options.NotNull(),_options.Limit(60));
                t.varchar("pass", _options.NotNull(),_options.Limit(32));
                t.varchar("mail", _options.Limit(64));
                t.clob("data");
                t.integer("created");
                t.integer("access");
                t.integer("login");
                t.smallint("status", _options.NotNull(), _options.Default("1"));
            }
        });

        _helper.commentTable("users", _options.Comment("用户信息"));
        _helper.commentColumn("users", "id", _options.Comment("主键"));
        _helper.commentColumn("users", "name", _options.Comment("姓名"));
        _helper.commentColumn("users", "pass", _options.Comment("密码"));
        _helper.commentColumn("users", "mail", _options.Comment("电子邮件"));
        _helper.commentColumn("users", "data", _options.Comment("存放人员数据"));
        _helper.commentColumn("users", "created", _options.Comment("创建时间，为秒数"));
        _helper.commentColumn("users", "access", _options.Comment("最后一次访问时间，为秒数"));
        _helper.commentColumn("users", "login", _options.Comment("最后一次成功登录的时间，为秒数"));
        _helper.commentColumn("users", "status", _options.Comment("状态,1为正常，0为拒绝"));
        _helper.addIndex("users","name");
    }

    @Override
    public void down() throws Throwable {
        _helper.dropTable("users");
        _helper.dropSequence("users_seq");
    }
}
