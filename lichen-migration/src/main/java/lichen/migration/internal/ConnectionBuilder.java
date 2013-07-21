package lichen.migration.internal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * build connection
 * @author jcai
 */
class ConnectionBuilder {
    private final String url;
    private final String username;
    private final String password;

    ConnectionBuilder(String url,String username,String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }
    <R> R withConnection(final ResourceUtils.CommitBehavior commitBehavior, final Function1<Connection, R> f){
        Connection connection;
        try {
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
