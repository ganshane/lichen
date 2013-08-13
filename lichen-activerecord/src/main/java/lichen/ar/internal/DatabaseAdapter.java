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
 * 针对不通数据库的适配器
 * @author jcai
 */
public class DatabaseAdapter {
    private final Map<Integer,FieldType<?>> types = new HashMap<Integer,FieldType<?>>();
    private final DataSource dataSource;
    DatabaseAdapter(DataSource dataSource){
        this.dataSource = dataSource;
    }
    public void registerTypes(){
        types.put( Types.BIGINT, LichenArTypes.BIG_INTEGER);
        types.put( Types.BINARY, LichenArTypes.BINARY);
        types.put( Types.BIT, LichenArTypes.BIT);
        types.put( Types.CHAR, LichenArTypes.CHAR);
        types.put( Types.DATE, LichenArTypes.DATE);
        types.put( Types.DOUBLE, LichenArTypes.DOUBLE);
        types.put( Types.FLOAT, LichenArTypes.FLOAT);
        types.put( Types.INTEGER, LichenArTypes.INTEGER);
        types.put( Types.SMALLINT, LichenArTypes.SMALLINT);
        types.put( Types.TINYINT, LichenArTypes.TINYINT);
        types.put( Types.TIME, LichenArTypes.TIME);
        types.put( Types.TIMESTAMP, LichenArTypes.TIMESTAMP);
        types.put( Types.VARCHAR, LichenArTypes.VARCHAR);
        types.put( Types.VARBINARY, LichenArTypes.VARBINARY);
        types.put( Types.NUMERIC, LichenArTypes.NUMERIC);
        types.put( Types.DECIMAL, LichenArTypes.DECIMAL);
        types.put( Types.BLOB, LichenArTypes.BLOB);
        types.put( Types.CLOB, LichenArTypes.CLOB);
    }

    JdbcHelper createJdbcHelper() {
        return new JdbcHelperImpl(dataSource);
    }
}
