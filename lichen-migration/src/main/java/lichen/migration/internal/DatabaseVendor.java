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

import java.sql.Driver;
import java.sql.DriverManager;

/**
 * 数据库的厂商.
 *
 * @author jcai
 */
public enum DatabaseVendor {
    H2,
    ORACLE
    ;

    public static DatabaseVendor forDriver(String driverClassName) {
        if (driverClassName.equals("org.h2.Driver")) {
            try {
                DriverManager.registerDriver((Driver) Class.forName(
                        "org.h2.Driver").newInstance());
            } catch (Throwable e) {
                throw new MigrationException(e);
            }
            return H2;
        } else if (driverClassName.equals("oracle.jdbc.driver.OracleDriver")) {
        	try {
                DriverManager.registerDriver((Driver) Class.forName(
                        "oracle.jdbc.driver.OracleDriver").newInstance());
            } catch (Throwable e) {
                throw new MigrationException(e);
            }
            return ORACLE;
        }
        throw new IllegalArgumentException("Must pass a non-null JDBC "
                + "driver class name to this " + "function.");
    }
}
