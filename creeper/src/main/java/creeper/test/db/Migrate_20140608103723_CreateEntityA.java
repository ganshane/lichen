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

import javax.inject.Inject;

import lichen.migration.model.TableDefinition;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;
import lichen.migration.services.TableCallback;

/**
 * @author jcai
 */
public class Migrate_20140608103723_CreateEntityA implements Migration {
    @Inject
    private MigrationHelper _helper;
    @Inject
    private Options _options;

    @Override
    public void up() throws Throwable {
        _helper.createTable("entity_a",new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.integer("entity_id", _options.PrimaryKey());
                t.integer("balance");
            }
        });

        _helper.commentTable("entity_a", _options.Comment("用于测试的实体表"));
        _helper.commentColumn("entity_a", "entity_id", _options.Comment("主键字段"));
        _helper.commentColumn("entity_a", "balance", _options.Comment("测试的整数字段"));
        _helper.createSequence("entity_a_seq");

    }

    @Override
    public void down() throws Throwable {
        _helper.dropTable("test_table");
        _helper.dropSequence("entity_a_seq");
    }
}
