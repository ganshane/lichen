package lichen.node.db;

import lichen.migration.model.TableDefinition;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;
import lichen.migration.services.TableCallback;

import javax.inject.Inject;

/**
 * @author jcai
 */
public class Migrate_20140620113412_CreateNodeTable implements Migration{
    @Inject
    private MigrationHelper _helper;
    @Inject
    private Options _options;

    @Override
    public void up() throws Throwable {
        _helper.createTable("nodes",new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.varbinary("id",_options.PrimaryKey(),_options.Limit(16));
                t.varchar("type",_options.NotNull(),_options.Limit(32));
                t.varchar("title",_options.NotNull(),_options.Limit(128));
                t.varchar("last_revision_id",_options.Limit(32));
                t.varbinary("user_id",_options.NotNull(),_options.Limit(16));
                t.integer("created",_options.NotNull());
                t.integer("changed",_options.NotNull());
                t.integer("comment",_options.NotNull(),_options.Default("0"));
            }
        });
        _helper.commentColumn("nodes","type","类型");
        _helper.commentColumn("nodes","title","标题");
        _helper.commentColumn("nodes","last_revision_id","最后一次版本的Id");
        _helper.commentColumn("nodes", "user_id", "创建用户");
        _helper.commentColumn("nodes","created","创建时间");
        _helper.commentColumn("nodes","changed","修改时间");
        _helper.commentColumn("nodes","comment","评论个数");
        _helper.addIndex("nodes", "type");
        _helper.addIndex("nodes", new String[]{"title", "type"});
        _helper.addIndex("nodes", "user_id");
        _helper.addIndex("nodes", "last_revision_id");

        _helper.createTable("node_revisions",new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.varbinary("id",_options.PrimaryKey(),_options.Limit(16));
                t.varchar("title", _options.NotNull(), _options.Limit(128));
                t.clob("body",_options.NotNull());
                t.varchar("log",_options.Limit(255));
                t.varbinary("node_id",_options.Limit(16));
                t.varbinary("user_id",_options.NotNull(),_options.Limit(16));
                t.integer("created",_options.NotNull());
            }
        });

        _helper.commentTable("node_revisions","节点的历史版本");
        _helper.commentColumn("node_revisions", "title", "标题");
        _helper.commentColumn("node_revisions", "body", "内容");
        _helper.commentColumn("node_revisions", "log", "注释");
        _helper.commentColumn("node_revisions", "node_id", "节点ID");
        _helper.commentColumn("node_revisions", "user_id", "用户ID");
        _helper.commentColumn("node_revisions","created","创建时间");
    }

    @Override
    public void down() throws Throwable {
        _helper.removeIndex("nodes", "type");
        _helper.removeIndex("nodes", new String[]{"title", "type"});
        _helper.removeIndex("nodes", "user_id");
        _helper.removeIndex("nodes", "last_revision_id");
        _helper.dropTable("nodes");
        _helper.dropTable("node_revisions");
    }
}
