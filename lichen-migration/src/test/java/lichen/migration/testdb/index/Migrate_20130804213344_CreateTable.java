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
package lichen.migration.testdb.index;

import lichen.migration.model.TableDefinition;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;
import lichen.migration.services.TableCallback;

import javax.inject.Inject;

/**
 * @author jcai
 */
public class Migrate_20130804213344_CreateTable implements Migration {
    @Inject
    private MigrationHelper _helper;
    @Inject
    private Options _options;

    @Override
    public void up() throws Throwable {
        _helper.createTable("test_table", new TableCallback() {
            @Override
            public void doInTable(TableDefinition t) throws Throwable {
                t.varchar("field1");
                t.varchar("field2");
                t.varchar("field3");
                t.varchar("field4");
                t.varchar("field5");
                t.varchar("field6");
                t.varchar("field7");
            }
        });
    }

    @Override
    public void down() throws Throwable {
        _helper.dropTable("test_table");
    }
}
