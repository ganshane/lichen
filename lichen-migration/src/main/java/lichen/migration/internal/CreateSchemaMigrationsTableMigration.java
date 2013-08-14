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
package lichen.migration.internal;

import lichen.migration.model.TableDefinition;
import lichen.migration.services.*;

import javax.inject.Inject;

/**
 * 创建数据库初始化脚本
 * @author jcai
 */
public class CreateSchemaMigrationsTableMigration  implements Migration {
    @Inject
    private MigrationHelper helper;
    @Inject
    private Options options;

    public void up() throws Throwable {
        helper.createTable(Migrator.schemaMigrationsTableName, new TableCallback() {
            public void doInTable(TableDefinition t) throws Throwable {
                t.varchar("version", options.Limit(32), options.NotNull());
            }
        });

        /*
        addIndex(Migrator.schemaMigrationsTableName,
                Array("version"),
                Unique,
                Name("unique_schema_migrations"))
        */
    }

    public void down() {
        throw new IllegalStateException("Fail to down");
    }
}
