package lichen.migration.internal;

/**
 * 数据库的厂商
 * @author jcai
 */
enum DatabaseVendor {
    H2;
    public static DatabaseVendor forDriver(String driverClassName){
        if(driverClassName.equals("org.h2.Driver")) {
            return H2;
        }
        throw new IllegalArgumentException("Must pass a non-null JDBC " +
                "driver class name to this " +
                "function.");
    }
}
