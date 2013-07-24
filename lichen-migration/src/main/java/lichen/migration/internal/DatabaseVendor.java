// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.internal;

import java.sql.Driver;
import java.sql.DriverManager;

/**
 * 数据库的厂商
 * @author jcai
 */
enum DatabaseVendor {
    H2;
    public static DatabaseVendor forDriver(String driverClassName){
        if(driverClassName.equals("org.h2.Driver")) {
            try {
                DriverManager.registerDriver((Driver) Class.forName("org.h2.Driver").newInstance());
            } catch (Throwable e) {
                throw new MigrationException(e);
            }
            return H2;
        }
        throw new IllegalArgumentException("Must pass a non-null JDBC " +
                "driver class name to this " +
                "function.");
    }
}
