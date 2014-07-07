package lichen.node.db;

import lichen.migration.model.TableDefinition;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;
import lichen.migration.services.TableCallback;

import javax.inject.Inject;

/**
 * 创建注释表
 * @author jcai
 */
public class Migrate_20140620133624_CreateComment implements Migration{
    @Inject
    private MigrationHelper _helper;
    @Inject
    private Options _options;

    @Override
    public void up() throws Throwable {
        _helper.createTable("comments",new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.varbinary("id",_options.PrimaryKey(),_options.Limit(32));
                t.varchar("title",_options.NotNull(),_options.Limit(128));
                t.clob("body",_options.NotNull());
                t.varchar("thread",_options.NotNull(),_options.Limit(128));
                t.varbinary("node_id",_options.NotNull(),_options.Limit(32));
                t.varbinary("user_id",_options.NotNull(),_options.Limit(32));
                t.varbinary("parent_id",_options.Limit(32));
                t.integer("created",_options.NotNull());
            }
        });
        _helper.commentTable("comments", "注释，评论");
        _helper.commentColumn("comments", "title", "标题");
        _helper.commentColumn("comments", "body", "内容");
        _helper.commentColumn("comments", "thread", "排序使用");
        _helper.commentColumn("comments", "node_id", "关联的节点ID");
        _helper.commentColumn("comments", "user_id", "关联的用户ID");
        _helper.commentColumn("comments", "parent_id", "关联的主注释ID");
        _helper.commentColumn("comments", "created", "创建时间");

        _helper.addIndex("comments", "node_id");
    }

    @Override
    public void down() throws Throwable {
        _helper.dropTable("comments");
    }
}
