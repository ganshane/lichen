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

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * build connection
 * @author jcai
 */
class ConnectionBuilder {
    private String url;
    private String username;
    private String password;
    private DataSource dataSource;

    ConnectionBuilder(String url,String username,String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public ConnectionBuilder(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    <R> R withConnection(final ResourceUtils.CommitBehavior commitBehavior, final Function1<Connection, R> f){
        Connection connection;
        try {
            if(dataSource != null){
                connection = dataSource.getConnection();
            }else
                connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ResourceUtils.autoClosingConnection(connection,new Function1<Connection, R>() {
            public R apply(Connection parameter) throws Throwable {
                return ResourceUtils.autoCommittingConnection(parameter,commitBehavior,f);
            }
        });
    }
}
