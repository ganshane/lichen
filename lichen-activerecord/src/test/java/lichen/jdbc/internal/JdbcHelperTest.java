package lichen.jdbc.internal;

import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

/**
 * Author: Erdinc YILMAZEL
 * Date: Dec 25, 2008
 * Time: 2:09:14 PM
 */
public class JdbcHelperTest {
   static SimpleDataSource dataSource;
   static JdbcHelper jdbc;

   @BeforeClass
   public static void setUp() {
      dataSource = new SimpleDataSource("org.h2.Driver",
         "jdbc:h2:mem:testdb",
         "sa", null);

      jdbc = new JdbcHelper(dataSource);

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


   @Test
   public void testQueryForList() {
      ArrayList<Bean> list = jdbc.queryForList("select * from jdbctest", new BeanCreator<Bean>() {
         public Bean createBean(ResultSet rs) throws SQLException {
            Bean bean = new Bean();
            bean.id = rs.getInt("id");
            bean.name = rs.getString("name");
            bean.creationDate = rs.getTimestamp("creation_date");
            return bean;
         }
      });

      assertNotNull(list);
      assertEquals("Testing query for list", 2, list.size());
   }

   @Test
   public void testQueryForObject() {
      Bean bean = jdbc.queryForObject("select * from jdbctest where id = ?", new BeanCreator<Bean>() {
         public Bean createBean(ResultSet rs) throws SQLException {
            Bean bean = new Bean();
            bean.id = rs.getInt("jdbctest.id");
            bean.name = rs.getString("jdbctest.name");
            bean.creationDate = rs.getTimestamp("jdbctest.creation_date");
            return bean;
         }
      }, 1);

      assertNotNull(bean);
      assertEquals(1, bean.id);
      assertEquals("Testing query for object", "erdinc", bean.name);
   }

   @Test
   public void testQueryForInt() {
      assertEquals("Testing query for int", 1, jdbc.queryForInt("select id from jdbctest"));
   }

   @Test
   public void testQueryForString() {
      assertEquals("Testing query for string", "erdinc", jdbc.queryForString("select name from jdbctest"));
   }

   @Test
   public void testQueryForLong() {
      assertEquals("Testing query for long", 1923232323L, jdbc.queryForLong("select big from jdbctest"));
   }

   @Test
   public void testQuery() {
      jdbc.query("select * from jdbctest", new ResultSetHandler() {
         public void processRow(ResultSet rs) throws SQLException {
            System.out.println("Name: " + rs.getString("name"));
         }
      });
   }

   @Test
   public void testQueryResult() {
      QueryResult result = jdbc.query("select * from jdbctest");
      
      while(result.next()) {
         System.out.println("Name: " + result.getString("name"));
      }

      result.close();
   }

   @Test
   public void testQueryWithFetchSize() {
      jdbc.query("select * from jdbctest", new ResultSetHandler(10) {
         public void processRow(ResultSet rs) throws SQLException {
            System.out.println("Name: " + rs.getString("name"));
         }
      });
   }

   @Test
   public void testInsert() {
      jdbc.execute("INSERT INTO `jdbctest` (name, creation_date, big, money, sm, data) " +
         "VALUES (?, ?, ?, ?, ?, ?)",
         "bahar",
         new Timestamp(System.currentTimeMillis()),
         1923232323L,
         new BigDecimal(10.4),
         true,
         new byte[]{1, 2, 3});

      assertEquals("Testing last insert id", 3, jdbc.getLastInsertId());
   }

   @Test
   public void testBatchInsert() {
      ArrayList<Bean> beans = new ArrayList<Bean>();
      Bean b = new Bean();
      b.name = "erdinc";
      b.creationDate = new Timestamp(System.currentTimeMillis());
      b.big = 234234234L;
      b.money = new BigDecimal(29.9);
      b.sm = true;
      b.data = new byte[]{1, 2, 3};
      beans.add(b);
      beans.add(b);


      int[] res = jdbc.executeBatch("INSERT INTO `jdbctest` (name, creation_date, big, money, sm, data) " +
         "VALUES (?, ?, ?, ?, ?, ?)", new IteratorBatchFeeder<Bean>(beans.iterator()) {

         public void feedStatement(PreparedStatement stmt, Bean object) throws SQLException {
            stmt.setString(1, object.name);
            stmt.setTimestamp(2, object.creationDate);
            stmt.setLong(3, object.big);
            stmt.setBigDecimal(4, object.money);
            stmt.setBoolean(5, object.sm);
            stmt.setBytes(6, object.data);
         }
      });

      assertNotNull(res);

      assertEquals(2, res.length);
   }

   @Test
   public void testBatchInsertUsingMapper() {
      ArrayList<Bean> beans = new ArrayList<Bean>();
      Bean b = new Bean();
      b.name = "erdinc";
      b.creationDate = new Timestamp(System.currentTimeMillis());
      b.big = 234234234L;
      b.money = new BigDecimal(29.9);
      b.sm = true;
      b.data = new byte[]{1, 2, 3};
      beans.add(b);
      beans.add(b);

      int[] res = jdbc.executeBatch("INSERT INTO `jdbctest` (name, creation_date, big, money, sm, data) " +
         "VALUES (?, ?, ?, ?, ?, ?)", new MappingBatchFeeder<Bean>(beans.iterator(), Bean.getStatementMapper()));

      assertNotNull(res);

      assertEquals(2, res.length);
   }
}
