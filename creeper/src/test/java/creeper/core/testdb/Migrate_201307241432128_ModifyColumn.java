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
package creeper.core.testdb;

import lichen.migration.model.SqlType;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;

import javax.inject.Inject;

/**
 * @author jcai
 */
public class Migrate_201307241432128_ModifyColumn implements Migration {
    @Inject
    private MigrationHelper _helper;
    @Inject
    private Options _options;

    @Override
    public void up() throws Throwable {
        final int size = 10;
        _helper.alterColumn("test_table", "test_col",
                SqlType.DecimalType,
                _options.NotNull(),
                _options.Precision(size),
                _options.Scale(2));
    }

    @Override
    public void down() throws Throwable {
        _helper.alterColumn("test_table", "test_col", SqlType.VarcharType, _options.NotNull());
    }
}
