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
package lichen.ar.internal;

import lichen.ar.model.Field;
import lichen.ar.services.PersisterHelper;
import lichen.jdbc.services.JdbcHelper;

import java.util.Map;

/**
 * 持久化的帮助类.
 *
 * @author jcai
 */
public class PersisterHelperImpl implements PersisterHelper {

    //数据库操作类
    private DatabaseAdapter _databaseAdapter;

    public PersisterHelperImpl(DatabaseAdapter databaseAdapter) {
        this._databaseAdapter = databaseAdapter;
    }

    @Override
    public Map<String, Field<?>> findTableFields(String tableName) {
        //TODO 利用jdbchelper中的方法来获得数据库中某一张表的字段定义
        @SuppressWarnings("unused")
        JdbcHelper helper = _databaseAdapter.createJdbcHelper();
        return null;
    }
}
