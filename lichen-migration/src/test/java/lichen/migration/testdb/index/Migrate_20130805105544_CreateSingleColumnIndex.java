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

import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;

import javax.inject.Inject;

/** 
 * @Description: 在单个字段上创建索引的测试用例脚本.
 *
 * @author zhaoyong
 *
 * @date 2013-8-5 上午10:56:32
 *
 * @version 1.0
 */
public class Migrate_20130805105544_CreateSingleColumnIndex implements Migration {
    @Inject
    private MigrationHelper _helper;
    @Inject
    private Options _options;

    @Override
    public void up() throws Throwable {
        //创建唯一索引
        _helper.addIndex("test_table", "field4", _options.Unique());
    }

    @Override
    public void down() throws Throwable {
        //删除未指定名称的索引（单列）
        _helper.removeIndex("test_table", "field4");
    }
}
