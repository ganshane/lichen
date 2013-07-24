// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
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
