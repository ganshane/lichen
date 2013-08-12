package lichen.ar.internal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;

import junit.framework.Assert;
import lichen.jdbc.internal.JdbcHelperImpl;
import lichen.jdbc.internal.SimpleDataSource;
import lichen.jdbc.services.JdbcHelper;
import lichen.jdbc.services.PreparedStatementSetter;
import lichen.jdbc.services.ResultSetGetter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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

    @Test
    public void test_binary() {
    	byte[] t = jdbc.queryForFirst("select data from jdbctest where id = 1 and data=?", new ResultSetGetter<byte[]>() {
			@Override
			public byte[] get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.BINARY.get(rs,1);
			}
		},new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				byte b[] = {1,2,3};
				LichenArTypes.BINARY.set(ps, 1, b);
			}
		});
    	Assert.assertEquals(t.length,3);
    }
    
    @Test
    public void test_bit() {
    	boolean b = jdbc.queryForFirst("select flag from jdbctest where id = 1 and flag=?", new ResultSetGetter<Boolean>() {
			@Override
			public Boolean get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.BIT.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				LichenArTypes.BIT.set(ps, 1, true);
			}
		});
    	Assert.assertEquals(b, true);
    }
    @Test
    public void test_char() {
    	char[] s = jdbc.queryForFirst("select descrip from jdbctest where id = 1 and descrip = ?", new ResultSetGetter<char[]>() {

			@Override
			public char[] get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.CHAR.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				LichenArTypes.CHAR.set(ps, 1, new char[]{'c','b','d','d','d','d'});
			}
		});
    	Assert.assertEquals(s[0], 'c');
    	Assert.assertEquals(s.length, 6);
    }
    @Test
    public void test_date() {
    	java.util.Date d = jdbc.queryForFirst("select create_date2 from jdbctest where id = 1 and create_date2 = ?", new ResultSetGetter<java.util.Date>() {
			@Override
			public java.util.Date get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.DATE.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				LichenArTypes.DATE.set(ps, 1,new Date(13000000000000l));
			}
		});
    	Assert.assertEquals(new Date(13000000000000l).before(d),false);
    }

    @Test
    public void test_double() {
    	Double d = jdbc.queryForFirst("select ra from jdbctest where id = 1 and ra = ?", new ResultSetGetter<Double>() {
			@Override
			public Double get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.DOUBLE.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				LichenArTypes.DOUBLE.set(ps, 1,1.002);
			}
		});
    	Assert.assertEquals(d,1.002);
    }
    @Test
    public void test_float() {
    	Float d = jdbc.queryForFirst("select fl from jdbctest where id = 1 and fl = ?", new ResultSetGetter<Float>() {
			@Override
			public Float get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.FLOAT.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				LichenArTypes.FLOAT.set(ps, 1,3.2f);
			}
		});
    	Assert.assertEquals(d,3.2f);
    }
    @Test
    public void test_integer() {
    	Integer d = jdbc.queryForFirst("select jkey from jdbctest where id = 1 and jkey = ?", new ResultSetGetter<Integer>() {
			@Override
			public Integer get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.INTEGER.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				LichenArTypes.INTEGER.set(ps, 1,10);
			}
		});
    	Assert.assertEquals(d.intValue(),10);
    }
    @Test
    public void test_smallint() {
    	Short d = jdbc.queryForFirst("select smi from jdbctest where id = 1 and smi = ?", new ResultSetGetter<Short>() {
			@Override
			public Short get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.SMALLINT.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				LichenArTypes.SMALLINT.set(ps, 1,Short.valueOf("2"));
			}
		});
    	Assert.assertEquals(d.intValue(),2);
    }
    @Test
    public void test_tinyint() {
    	Byte b = jdbc.queryForFirst("select sm from jdbctest where id = 1 and sm=?", new ResultSetGetter<Byte>() {
			@Override
			public Byte get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.TINYINT.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				LichenArTypes.TINYINT.set(ps, 1,new Byte("1"));
			}
		});
    	Assert.assertEquals(b.byteValue(),1);
    }
    @Test
    public void test_time() {
    	java.util.Date d = jdbc.queryForFirst("select create_time from jdbctest where id = 1 and create_time < ?", new ResultSetGetter<java.util.Date>() {
			@Override
			public java.util.Date get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.TIME.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				LichenArTypes.TIME.set(ps, 1,new java.util.Date(System.currentTimeMillis()));
			}
		});
    	Assert.assertEquals(new Date(System.currentTimeMillis()).before(d),false);
    }
    @Test
    public void test_timestamp() {
    	java.util.Date d = jdbc.queryForFirst("select creation_date from jdbctest where id = 1 and creation_date < ?", new ResultSetGetter<java.util.Date>() {
			@Override
			public java.util.Date get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.TIMESTAMP.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				LichenArTypes.TIMESTAMP.set(ps, 1,new java.util.Date(System.currentTimeMillis()));
			}
		});
    	Assert.assertEquals(new Date(System.currentTimeMillis()).before(d),false);
    }
    @Test
    public void test_varchar() {
    	String d = jdbc.queryForFirst("select name from jdbctest where id = 1 and name = ?", new ResultSetGetter<String>() {
			@Override
			public String get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.VARCHAR.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				LichenArTypes.VARCHAR.set(ps, 1,"erdinc");
			}
		});
    	Assert.assertEquals(d,"erdinc");
    }
    @Test
    public void test_varbinary() {
    	byte[] d = jdbc.queryForFirst("select data from jdbctest where id = 1 and data = ?", new ResultSetGetter<byte[]>() {
			@Override
			public byte[] get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.VARBINARY.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				LichenArTypes.VARBINARY.set(ps, 1,new byte[]{1,2,3});
			}
		});
    	Assert.assertEquals(d.length ,3);
    }
    @Test
    public void test_numeric() {
    	BigDecimal d = jdbc.queryForFirst("select nmc from jdbctest where id = 1 and nmc = ?", new ResultSetGetter<BigDecimal>() {
			@Override
			public BigDecimal get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.NUMERIC.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				LichenArTypes.NUMERIC.set(ps, 1,new BigDecimal(1000));
			}
		});
    	Assert.assertEquals(d.intValue(),1000);
    }
    @Test
    public void test_decimal() {
    	BigDecimal d = jdbc.queryForFirst("select money from jdbctest where id = 1 and money=?", new ResultSetGetter<BigDecimal>() {
			@Override
			public BigDecimal get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.DECIMAL.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				LichenArTypes.DECIMAL.set(ps, 1,new BigDecimal("10.40"));
			}
		});
    	Assert.assertEquals(d.floatValue(),10.4f);
    }

    @Test
    public void test_blob() {
    	Blob b = jdbc.queryForFirst("select image from jdbctest where id = 1 and image=?", new ResultSetGetter<Blob>() {
			@Override
			public Blob get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.BLOB.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				byte[] by = new byte[]{2,3,4,5,6};
				SerialBlob cl = new SerialBlob(by);
				LichenArTypes.BLOB.set(ps, 1,cl);
			}
		});
    	
    	byte[] bb = null;
    	try {
    		bb = b.getBytes(1l, (int)b.length());
		} catch (Exception e) {
		}
    	Assert.assertEquals(bb.length,5);
    	Assert.assertEquals(bb[3],5);
    }

    @Test
    public void test_clob() {
    	Clob d = jdbc.queryForFirst("select context from jdbctest where id = 1 and context=?", new ResultSetGetter<Clob>() {
			@Override
			public Clob get(ResultSet rs, int index) throws SQLException {
				return LichenArTypes.CLOB.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index) throws SQLException {
				char[] ch = new char[]{'c','l','o','b'};
				SerialClob cl = new SerialClob(ch);
				LichenArTypes.CLOB.set(ps, 1,cl);
			}
		});
    	String cont = null;
    	try {
			cont = d.getSubString(1l, (int)d.length());
		} catch (Exception e) {
		}
    	Assert.assertEquals(cont,"clob");
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
                "`flag` bit,\n" +
                "`descrip` char(1000),\n" +
                "`ra` double,\n" + 
                "`fl` float,\n" +
                "`smi` smallint,\n" +
                "`create_time` time,\n" +
                "`create_date2` date,\n" +
                "`nmc` numeric(10),\n" +
                "`image` blob,\n" +
                "`context` clob,\n" +
                " PRIMARY KEY  (`id`)\n" +
                ")");

        jdbc.execute("TRUNCATE TABLE `jdbctest`");

        jdbc.execute("INSERT INTO `jdbctest` (id, jkey, name, creation_date, big, money, sm, data,flag,descrip,ra,fl,smi,create_time,create_date2,nmc,image,context) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)",
                1, 10, "erdinc",
                new Timestamp(System.currentTimeMillis()),
                1923232323L,
                new BigDecimal(10.4),
                true,
                new byte[]{1, 2, 3},
                true,
                "cbdddd",
                new Double("1.002"),
                new Float("3.2"),
                2,
                new Date(System.currentTimeMillis()),
                new Date(13000000000000l),
                1000,
                new byte[]{2,3,4,5,6},
                "clob");

        jdbc.execute("INSERT INTO `jdbctest` (id, jkey, name, creation_date, big, money, sm, data,flag,descrip,ra,fl,smi,create_time,create_date2,nmc,image,context) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)",
                2, 10, "bahar",
                new Timestamp(System.currentTimeMillis()),
                1923232323L,
                new BigDecimal(10.4),
                true,
                new byte[]{1, 2, 3},
                false,
                'c',
                new Double("1.003"),
                new Float("3.3"),
                3,
                new Date(System.currentTimeMillis()),
                new Date(13000000000000l),
                1000,
                new byte[]{2,3,4,5,6},
                "clob");
    }

    @AfterClass
    public static void destroy() {
        jdbc.execute("DROP TABLE `jdbctest`");
        jdbc.releaseConnection();
    }
}
