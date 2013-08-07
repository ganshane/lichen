package lichen.jdbc.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lichen.jdbc.services.JdbcHelper;
import lichen.jdbc.services.PreparedStatementSetter;
import lichen.jdbc.services.ResultSetCallback;
import lichen.jdbc.services.ResultSetGetter;
import lichen.jdbc.services.RowMapper;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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
    private class Bean{
        int id;
        String name;
        Timestamp creationDate;
    }

   @Test
   public void testQueryForList() {
      List<Bean> list = jdbc.queryForList("select * from jdbctest", new RowMapper<Bean>() {
          @Override
          public Bean mapRow(ResultSet rs, int index) throws SQLException {
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
   public void testWithResultSet() {
	  Bean bean = jdbc.withResultSet("select * from jdbctest where id=? and jkey=? and name=?", new ResultSetCallback<Bean>() {
			@Override
			public Bean doInResultSet(ResultSet rs)
					throws SQLException {
				Bean bean = new Bean();
		        bean.id = rs.getInt("id");
		        bean.name = rs.getString("name");
		        bean.creationDate = rs.getTimestamp("creation_date");
		        return bean;
			}
		  },new PreparedStatementSetter(){
			  @Override
			  public void set(PreparedStatement ps,int index) throws SQLException {
				  ps.setObject(index, "1");
			  }
		  },new PreparedStatementSetter(){
			  @Override
			  public void set(PreparedStatement ps,int index) throws SQLException {
				  ps.setObject(index, "10");
			  }
		  },new PreparedStatementSetter(){
			  @Override
			  public void set(PreparedStatement ps,int index) throws SQLException {
				  ps.setObject(index, "erdinc");
			  }
		  });
		
		  assertNotNull(bean);
		  
		  Integer currentId = jdbc.withResultSet("select * from jdbctest", new ResultSetCallback<Integer>() {
				@Override
				public Integer doInResultSet(ResultSet rs)
						throws SQLException {
			        return rs.getInt("id");
				}
			  });
			
		  assertEquals(1, currentId.intValue());
   }

   @Test
   public void testQueryForList2() {
      List<Bean> list = jdbc.queryForList("select * from jdbctest where id=? and jkey=? and name=?", new RowMapper<Bean>() {
          @Override
          public Bean mapRow(ResultSet rs, int index) throws SQLException {
              Bean bean = new Bean();
              bean.id = rs.getInt("id");
              bean.name = rs.getString("name");
              bean.creationDate = rs.getTimestamp("creation_date");
              return bean;
          }
      },new PreparedStatementSetter(){
    	  @Override
    	  public void set(PreparedStatement ps,int index) throws SQLException {
    		  ps.setObject(index, "1");
    	  }
      },new PreparedStatementSetter(){
    	  @Override
    	  public void set(PreparedStatement ps,int index) throws SQLException {
    		  ps.setObject(index, "10");
    	  }
      },new PreparedStatementSetter(){
    	  @Override
    	  public void set(PreparedStatement ps,int index) throws SQLException {
    		  ps.setObject(index, "erdinc");
    	  }
      });

      assertNotNull(list);
      assertEquals("Testing query for list", 1, list.size());
   }
   
   @Test
	public void testqueryForFirst() {
		Bean b = jdbc.queryForFirst("select * from jdbctest where jkey=? order by id",
				new ResultSetGetter<Bean>() {
					public Bean get(ResultSet rs, int index)
							throws SQLException {
						  Bean bean = new Bean();
			              bean.id = rs.getInt("id");
			              bean.name = rs.getString("name");
			              bean.creationDate = rs.getTimestamp("creation_date");
			              return bean;
					}
				}, new PreparedStatementSetter() {
					@Override
					public void set(PreparedStatement ps, int index)
							throws SQLException {
						ps.setObject(index, "10");
					}
				});
		assertNotNull(b);
		assertEquals(1,b.id);
		
		int count = jdbc.queryForFirst("select count(*) from jdbctest where jkey=?",
				new ResultSetGetter<Integer>() {
					public Integer get(ResultSet rs, int index)
							throws SQLException {
						return rs.getInt(1);
					}
				}, new PreparedStatementSetter() {
					@Override
					public void set(PreparedStatement ps, int index)
							throws SQLException {
						ps.setObject(index, "10");
					}
				});
		assertEquals(2,count);
		
		long lCount = jdbc.queryForFirst("select count(*) from jdbctest where jkey=?",
				new ResultSetGetter<Long>() {
					public Long get(ResultSet rs, int index)
							throws SQLException {
						return rs.getLong(1);
					}
				}, new PreparedStatementSetter() {
					@Override
					public void set(PreparedStatement ps, int index)
							throws SQLException {
						ps.setObject(index, "10");
					}
				});
		assertEquals(2,lCount);
		
		String s = jdbc.queryForFirst("select name from jdbctest where jkey=? order by id desc ",
				new ResultSetGetter<String>() {
					public String get(ResultSet rs, int index) throws SQLException {
						return rs.getString(1);
					}
				}, new PreparedStatementSetter() {
					@Override
					public void set(PreparedStatement ps, int index)
							throws SQLException {
						ps.setObject(index, "10");
					}
				});
		assertEquals("bahar",s);
	}

    /*
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
   */
}
