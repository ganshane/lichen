package lichen.creeper.user.db;

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
                t.varbinary("id", _options.PrimaryKey(),_options.Limit(16));
                t.varchar("name", _options.NotNull(),_options.Limit(60));
                t.varchar("pass", _options.NotNull(),_options.Limit(500));
                t.varchar("mail", _options.Limit(64));
                t.clob("data");
                t.integer("created");
                t.integer("last_access");
                t.integer("login");
                t.smallint("status", _options.NotNull(), _options.Default("0"));
            }
        });

        _helper.commentTable("users", "用户信息");
        _helper.commentColumn("users", "id", "主键");
        _helper.commentColumn("users", "name", "姓名");
        _helper.commentColumn("users", "pass", "密码");
        _helper.commentColumn("users", "mail", "电子邮件");
        _helper.commentColumn("users", "data", "存放人员数据");
        _helper.commentColumn("users", "created", "创建时间，为秒数");
        _helper.commentColumn("users", "last_access", "最后一次访问时间，为秒数");
        _helper.commentColumn("users", "login", "最后一次成功登录的时间，为秒数");
        _helper.commentColumn("users", "status", "状态,0为正常，1为拒绝");
        _helper.addIndex("users","name");
    }

    @Override
    public void down() throws Throwable {
        _helper.removeIndex("users","name");
        _helper.dropTable("users");
    }
}
