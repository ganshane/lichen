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

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author jcai
 */
public class ConnectionBuilderTest {
    @Test
    public void testConn() {
        ConnectionBuilder connectionBuilder = new ConnectionBuilder("jdbc:h2:mem:test/db", "sa", "");
        int r = connectionBuilder.withConnection(ResourceUtils.CommitBehavior.AutoCommit,
                new Function1<Connection, Integer>() {
            public Integer apply(Connection connection) throws Throwable {
                PreparedStatement ps = connection.prepareStatement("create table a(name int);");
                return ResourceUtils.autoClosingStatement(ps, new Function1<PreparedStatement, Integer>() {
                    public Integer apply(PreparedStatement parameter) throws Throwable {
                        return parameter.executeUpdate();
                    }
                });
            }
        });
    }
}
