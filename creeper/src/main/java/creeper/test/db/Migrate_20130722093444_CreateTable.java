// Copyright 2013 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package creeper.test.db;

import lichen.migration.model.SqlType;
import lichen.migration.model.TableDefinition;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;
import lichen.migration.services.TableCallback;

import javax.inject.Inject;

/**
 * @author jcai
 */
public class Migrate_20130722093444_CreateTable implements Migration {
    @Inject
    private MigrationHelper _helper;
    @Inject
    private Options _options;

    @Override
    public void up() throws Throwable {
        final int size = 10;
        _helper.createTable("test_table", new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.bigint("int",_options.PrimaryKey());
                t.blob("blob");
//                t.bool("bool");
//                t.charColumn("charColumn");
//                t.column("column_", SqlType.BigintType);
                //t.decimal("decimal_",Op);
                t.integer("int_", _options.Unique());
//                t.smallint("sint_");
//                t.timestamp("ts");
//                t.varbinary("vb");
                t.varchar("vc", _options.Limit(size), _options.Default("'asdf'"));
            }
        });

        _helper.addColumn("test_table", "test_col", SqlType.VarcharType, _options.NotNull());
        
        _helper.commentTable("test_table", _options.Comment("AAAa啊啊"));
        _helper.commentColumn("test_table", "vc", _options.Comment("列1"));
        _helper.commentColumn("test_table", "blob", _options.Comment("照片"));

    }

    @Override
    public void down() throws Throwable {
        _helper.removeColumn("test_table", "vc");
        _helper.dropTable("test_table");
    }
}
