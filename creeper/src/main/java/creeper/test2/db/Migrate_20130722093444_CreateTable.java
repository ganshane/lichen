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
package creeper.test2.db;

import javax.inject.Inject;

import lichen.migration.model.TableDefinition;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;
import lichen.migration.services.TableCallback;

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
        _helper.createTable("test_table2", new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.varchar("vc", _options.Limit(size), _options.Default("'asdf'"));
            }
        });
    }

    @Override
    public void down() throws Throwable {
        _helper.removeColumn("test_table", "test_col");
        _helper.dropTable("test_table");
    }
}
