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

import lichen.ar.services.FieldType;
import lichen.jdbc.internal.JdbcHelperImpl;
import lichen.jdbc.services.JdbcHelper;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * 针对不通数据库的适配器.
 * @author jcai
 */
public class DatabaseAdapter {
    private Map<Integer, FieldType<?>> _types = new HashMap<Integer, FieldType<?>>();
    private DataSource _dataSource;
    DatabaseAdapter(DataSource dataSource) {
        this._dataSource = dataSource;
    }
    public void registerTypes() {
        _types.put(Types.BIGINT, LichenArTypes.BIG_INTEGER);
        _types.put(Types.BINARY, LichenArTypes.BINARY);
        _types.put(Types.BIT, LichenArTypes.BIT);
        _types.put(Types.CHAR, LichenArTypes.CHAR);
        _types.put(Types.DATE, LichenArTypes.DATE);
        _types.put(Types.DOUBLE, LichenArTypes.DOUBLE);
        _types.put(Types.FLOAT, LichenArTypes.FLOAT);
        _types.put(Types.INTEGER, LichenArTypes.INTEGER);
        _types.put(Types.SMALLINT, LichenArTypes.SMALLINT);
        _types.put(Types.TINYINT, LichenArTypes.TINYINT);
        _types.put(Types.TIME, LichenArTypes.TIME);
        _types.put(Types.TIMESTAMP, LichenArTypes.TIMESTAMP);
        _types.put(Types.VARCHAR, LichenArTypes.VARCHAR);
        _types.put(Types.VARBINARY, LichenArTypes.BINARY);
        _types.put(Types.NUMERIC, LichenArTypes.NUMERIC);
        _types.put(Types.DECIMAL, LichenArTypes.DECIMAL);
        _types.put(Types.BLOB, LichenArTypes.BLOB);
        _types.put(Types.CLOB, LichenArTypes.CLOB);
    }

    JdbcHelper createJdbcHelper() {
        return new JdbcHelperImpl(_dataSource);
    }
}
