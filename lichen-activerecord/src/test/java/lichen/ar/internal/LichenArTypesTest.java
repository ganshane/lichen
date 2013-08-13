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
 * lichen active record type test.
 * @author jcai
 */
public class LichenArTypesTest {
    @Test
    public void testbigint() {
        BigInteger r = jdbc.queryForFirst(
        		"select big from jdbctest where id = 1 and big=?",
        		new ResultSetGetter<BigInteger>() {
            @Override
            public BigInteger get(ResultSet rs, int index) throws SQLException {
                return LichenArTypes.BIG_INTEGER.get(rs, 1);
            }
        }, new PreparedStatementSetter() {
                    @Override
                    public void set(PreparedStatement ps, int index)
                    throws SQLException {
                        LichenArTypes.BIG_INTEGER.set(ps, index,
                        		new BigInteger("1923232323"));
                    }
                });
        final long c = 1923232323L;
        Assert.assertEquals(r.longValue(), c);
    }

    @Test
    public void testbinary() {
    	byte[] t = jdbc.queryForFirst(
    			"select data from jdbctest where id = 1 and data=?",
    			new ResultSetGetter<byte[]>() {
			@Override
			public byte[] get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.BINARY.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				final int i = 3;
				byte[] b = {1, 2, i};
				LichenArTypes.BINARY.set(ps, 1, b);
			}
		});
    	final int len = 3;
    	Assert.assertEquals(t.length, len);
    }

    @Test
    public void testbit() {
    	boolean b = jdbc.queryForFirst(
    			"select flag from jdbctest where id = 1 and flag=?",
    			new ResultSetGetter<Boolean>() {
			@Override
			public Boolean get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.BIT.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				LichenArTypes.BIT.set(ps, 1, true);
			}
		});
    	Assert.assertEquals(b, true);
    }
    @Test
    public void testchar() {
    	char[] s = jdbc.queryForFirst(
    	    "select descrip from jdbctest where id = 1 and descrip = ?",
    		new ResultSetGetter<char[]>() {

			@Override
			public char[] get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.CHAR.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				LichenArTypes.CHAR.set(ps, 1,
				    new char[] {'c', 'b', 'd', 'd', 'd', 'd'});
			}
		});
    	Assert.assertEquals(s[0], 'c');
    	final int len = 6;
    	Assert.assertEquals(s.length, len);
    }
    @Test
    public void testdate() {
    	final long t = 13000000000000L;
    	java.util.Date d = jdbc.queryForFirst(
    	"select create_date2 from jdbctest where id = 1 and create_date2 = ?",
    	new ResultSetGetter<java.util.Date>() {
			@Override
			public java.util.Date get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.DATE.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				LichenArTypes.DATE.set(ps, 1, new Date(t));
			}
		});
    	Assert.assertEquals(new Date(t).before(d), false);
    }

    @Test
    public void testdouble() {
    	final double dbl = 1.002;
    	Double d = jdbc.queryForFirst(
    			"select ra from jdbctest where id = 1 and ra = ?",
    			new ResultSetGetter<Double>() {
			@Override
			public Double get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.DOUBLE.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				LichenArTypes.DOUBLE.set(ps, 1, dbl);
			}
		});
    	Assert.assertEquals(d, dbl);
    }

    @Test
    public void testfloat() {
    	final float fat = 3.2f;
    	Float d = jdbc.queryForFirst(
    			"select fl from jdbctest where id = 1 and fl = ?",
    			new ResultSetGetter<Float>() {
			@Override
			public Float get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.FLOAT.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				LichenArTypes.FLOAT.set(ps, 1, fat);
			}
		});
    	Assert.assertEquals(d, fat);
    }

    @Test
    public void testinteger() {
    	final int i = 10;
    	Integer d = jdbc.queryForFirst(
    			"select jkey from jdbctest where id = 1 and jkey = ?",
    			new ResultSetGetter<Integer>() {
			@Override
			public Integer get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.INTEGER.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				LichenArTypes.INTEGER.set(ps, 1, i);
			}
		});
    	Assert.assertEquals(d.intValue(), i);
    }
    @Test
    public void testsmallint() {
    	Short d = jdbc.queryForFirst(
    			"select smi from jdbctest where id = 1 and smi = ?",
    			new ResultSetGetter<Short>() {
			@Override
			public Short get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.SMALLINT.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				LichenArTypes.SMALLINT.set(ps, 1,
						Short.valueOf("2"));
			}
		});
    	Assert.assertEquals(d.intValue(), 2);
    }
    @Test
    public void testtinyint() {
    	Byte b = jdbc.queryForFirst(
    			"select sm from jdbctest where id = 1 and sm=?",
    			new ResultSetGetter<Byte>() {
			@Override
			public Byte get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.TINYINT.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				LichenArTypes.TINYINT.set(ps, 1, new Byte("1"));
			}
		});
    	Assert.assertEquals(b.byteValue(), 1);
    }
    @Test
    public void testtime() {
    	java.util.Date d = jdbc.queryForFirst(
    	"select create_time from jdbctest where id = 1 and create_time < ?",
    	new ResultSetGetter<java.util.Date>() {
			@Override
			public java.util.Date get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.TIME.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				LichenArTypes.TIME.set(ps, 1,
				new java.util.Date(System.currentTimeMillis()));
			}
		});
    	Assert.assertEquals(
    			new Date(System.currentTimeMillis()).before(d), false);
    }
    @Test
    public void testtimestamp() {
    	java.util.Date d = jdbc.queryForFirst(
    	"select creation_date from jdbctest where id = 1 and creation_date < ?",
    	new ResultSetGetter<java.util.Date>() {
			@Override
			public java.util.Date get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.TIMESTAMP.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				LichenArTypes.TIMESTAMP.set(
				ps, 1, new java.util.Date(
						System.currentTimeMillis()));
			}
		});
    	Assert.assertEquals(
    			new Date(System.currentTimeMillis()).before(d), false);
    }
    @Test
    public void testvarchar() {
    	String d = jdbc.queryForFirst(
    			"select name from jdbctest where id = 1 and name = ?",
    			new ResultSetGetter<String>() {
			@Override
			public String get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.VARCHAR.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				LichenArTypes.VARCHAR.set(ps, 1, "erdinc");
			}
		});
    	Assert.assertEquals(d, "erdinc");
    }
    @Test
    public void testvarbinary() {
    	final int i = 3;
    	byte[] d = jdbc.queryForFirst(
    			"select data from jdbctest where id = 1 and data = ?",
    			new ResultSetGetter<byte[]>() {
			@Override
			public byte[] get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.VARBINARY.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				LichenArTypes.VARBINARY.set(ps, 1,
						new byte[]{1, 2, i});
			}
		});
    	Assert.assertEquals(d.length, i);
    }
    @Test
    public void testnumeric() {
    	final int i = 1000;
    	BigDecimal d = jdbc.queryForFirst(
    			"select nmc from jdbctest where id = 1 and nmc = ?",
    			new ResultSetGetter<BigDecimal>() {
			@Override
			public BigDecimal get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.NUMERIC.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				LichenArTypes.NUMERIC.set(ps, 1,
						new BigDecimal(i));
			}
		});
    	Assert.assertEquals(d.intValue(), i);
    }
    @Test
    public void testdecimal() {
    	final float f = 10.4f;
    	BigDecimal d = jdbc.queryForFirst(
    			"select money from jdbctest where id = 1 and money=?",
    			new ResultSetGetter<BigDecimal>() {
			@Override
			public BigDecimal get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.DECIMAL.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				LichenArTypes.DECIMAL.set(ps, 1,
						new BigDecimal("10.4"));
			}
		});
    	Assert.assertEquals(d.floatValue(), f);
    }

    @Test
    public void testblob() {
    	final int x = 3;
    	final int y = 4;
    	final int z = 5;
    	final int t = 6;
    	Blob b = jdbc.queryForFirst(
    			"select image from jdbctest where id = 1 and image=?",
    			new ResultSetGetter<Blob>() {
			@Override
			public Blob get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.BLOB.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				byte[] by = new byte[]{2, x, y, z, t};
				SerialBlob cl = new SerialBlob(by);
				LichenArTypes.BLOB.set(ps, 1, cl);
			}
		});

    	byte[] bb = null;
    	try {
    		bb = b.getBytes(1L, (int) b.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	Assert.assertEquals(bb.length, z);
    	Assert.assertEquals(bb[x], z);
    }

    @Test
    public void testclob() {
    	Clob d = jdbc.queryForFirst(
    	"select context from jdbctest where id = 1 and context=?",
    	new ResultSetGetter<Clob>() {
			@Override
			public Clob get(ResultSet rs, int index)
			throws SQLException {
				return LichenArTypes.CLOB.get(rs, 1);
			}
		}, new PreparedStatementSetter() {
			@Override
			public void set(PreparedStatement ps, int index)
			throws SQLException {
				char[] ch = new char[]{'c', 'l', 'o', 'b'};
				SerialClob cl = new SerialClob(ch);
				LichenArTypes.CLOB.set(ps, 1, cl);
			}
		});
    	String cont = null;
    	try {
			cont = d.getSubString(1L, (int) d.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	Assert.assertEquals(cont, "clob");
    }
    private static SimpleDataSource dataSource;
    private static JdbcHelper jdbc;

    @BeforeClass
    public static void setUp() {
        dataSource = new SimpleDataSource("org.h2.Driver",
                "jdbc:h2:mem:testdb",
                "sa", null);

        jdbc = new JdbcHelperImpl(dataSource);

        jdbc.holdConnection();
        jdbc.execute("CREATE TABLE IF NOT EXISTS `jdbctest`("
        		+ "`id` int(11) NOT NULL auto_increment,\n"
        		+ "`jkey` int(11) NOT NULL DEFAULT 10,\n"
        		+ "`name` varchar(16) NOT NULL,\n"
        		+ "`creation_date` timestamp NOT NULL,\n"
        		+ "`big` bigint(20) NOT NULL,\n"
        		+ "`money` decimal(10,2) NOT NULL,\n"
        		+ "`sm` tinyint(1) NOT NULL,\n"
        		+ "`data` varbinary(128) NOT NULL,\n"
        		+ "`flag` bit,\n"
        		+ "`descrip` char(1000),\n"
        		+ "`ra` double,\n"
        		+ "`fl` float,\n"
        		+ "`smi` smallint,\n"
        		+ "`create_time` time,\n"
        		+ "`create_date2` date,\n"
        		+ "`nmc` numeric(10),\n"
        		+ "`image` blob,\n"
        		+ "`context` clob,\n"
        		+ " PRIMARY KEY  (`id`)\n"
        		+ ")");

        jdbc.execute("TRUNCATE TABLE `jdbctest`");

        final int three = 3;
        final int four = 4;
        final int five = 5;
        final int six = 6;
        final int ten = 10;
        final long l1 = 1923232323L;
        final long l2 = 13000000000000L;
        final long l3 = 1000;
        final float f2 = 10.4F;

        jdbc.execute("INSERT INTO `jdbctest` (id, jkey, name, creation_date,"
        	+ "big,money,sm,data,flag,descrip,ra,fl,smi,create_time,"
        	+ "create_date2,nmc,image,context) "
        	+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                1, ten, "erdinc",
                new Timestamp(System.currentTimeMillis()),
                l1,
                new BigDecimal(f2),
                true,
                new byte[]{1, 2, three},
                true,
                "cbdddd",
                new Double("1.002"),
                new Float("3.2"),
                2,
                new Date(System.currentTimeMillis()),
                new Date(l2),
                l3,
                new byte[]{2, three, four, five, six},
                "clob");

        jdbc.execute("INSERT INTO `jdbctest` (id, jkey, name, creation_date,"
        		+ " big, money, sm, data,flag,descrip,ra,fl,smi,"
        		+ "create_time,create_date2,nmc,image,context) "
        		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)",
                2, ten, "bahar",
                new Timestamp(System.currentTimeMillis()),
                l1,
                new BigDecimal(f2),
                true,
                new byte[]{1, 2, three},
                false,
                'c',
                new Double("1.003"),
                new Float("3.3"),
                three,
                new Date(System.currentTimeMillis()),
                new Date(l2),
                l3,
                new byte[]{2, three, four, five, six},
                "clob");
    }

    @AfterClass
    public static void destroy() {
        jdbc.execute("DROP TABLE `jdbctest`");
        jdbc.releaseConnection();
    }
}
