package lichen.ar.internal;

import junit.framework.Assert;
import lichen.jdbc.internal.JdbcHelperImpl;
import lichen.jdbc.internal.SimpleDataSource;
import lichen.jdbc.services.JdbcHelper;
import lichen.jdbc.services.PreparedStatementSetter;
import lichen.jdbc.services.ResultSetGetter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * lichen active record type test
 * @author jcai
 */
public class LichenArTypesTest {
    @Test
    public void test_bigint(){
        BigInteger r =jdbc.queryForFirst("select big from jdbctest where id = 1 and big=?",new ResultSetGetter<BigInteger>() {
            @Override
            public BigInteger get(ResultSet rs, int index) throws SQLException {
                return LichenArTypes.BIG_INTEGER.get(rs,1);
            }
        },new PreparedStatementSetter() {
                    @Override
                    public void set(PreparedStatement ps, int index) throws SQLException {
                        LichenArTypes.BIG_INTEGER.set(ps,index,new BigInteger("1923232323"));
                    }
                });
        Assert.assertEquals(r.longValue(),1923232323L);
    }


    static SimpleDataSource dataSource;
    static JdbcHelper jdbc;

    @BeforeClass
    public static void setUp() {
        dataSource = new SimpleDataSource("org.h2.Driver",
                "jdbc:h2:mem:testdb",
                "sa", null);

        jdbc = new JdbcHelperImpl(dataSource);

        jdbc.holdConnection();
        jdbc.execute("CREATE TABLE IF NOT EXISTS `jdbctest`(" +
                "`id` int(11) NOT NULL auto_increment,\n" +
                "`jkey` int(11) NOT NULL DEFAULT 10,\n" +
                "`name` varchar(16) NOT NULL,\n" +
                "`creation_date` timestamp NOT NULL,\n" +
                "`big` bigint(20) NOT NULL,\n" +
                "`money` decimal(10,2) NOT NULL,\n" +
                "`sm` tinyint(1) NOT NULL,\n" +
                "`data` varbinary(128) NOT NULL,\n" +
                " PRIMARY KEY  (`id`)\n" +
                ")");

        jdbc.execute("TRUNCATE TABLE `jdbctest`");

        jdbc.execute("INSERT INTO `jdbctest` (id, jkey, name, creation_date, big, money, sm, data) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                1, 10, "erdinc",
                new Timestamp(System.currentTimeMillis()),
                1923232323L,
                new BigDecimal(10.4),
                true,
                new byte[]{1, 2, 3});

        jdbc.execute("INSERT INTO `jdbctest` (id, jkey, name, creation_date, big, money, sm, data) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                2, 10, "bahar",
                new Timestamp(System.currentTimeMillis()),
                1923232323L,
                new BigDecimal(10.4),
                true,
                new byte[]{1, 2, 3});
    }

    @AfterClass
    public static void destroy() {
        jdbc.execute("DROP TABLE `jdbctest`");
        jdbc.releaseConnection();
    }
}
