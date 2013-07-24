// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.internal;

import junit.framework.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author jcai
 */
public class ConnectionBuilderTest {
    @Test
    public void test_conn(){
        ConnectionBuilder connectionBuilder = new ConnectionBuilder("jdbc:h2:mem:test/db","sa","");
        int r = connectionBuilder.withConnection(ResourceUtils.CommitBehavior.AutoCommit,new Function1<Connection, Integer>() {
            public Integer apply(Connection connection) throws Throwable {
                PreparedStatement ps = connection.prepareStatement("create table a(name int);");
                return ResourceUtils.autoClosingStatement(ps,new Function1<PreparedStatement, Integer>() {
                    public Integer apply(PreparedStatement parameter) throws Throwable {
                        return parameter.executeUpdate();
                    }
                });
            }
        });
    }
}
