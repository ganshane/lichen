package lichen.ar.internal;

import lichen.ar.services.FieldType;
import lichen.jdbc.internal.JdbcHelperImpl;
import lichen.jdbc.services.JdbcHelper;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * 针对不通数据库的适配器.
 * @author jcai
 */
public class DatabaseAdapter {
    private final Map<Integer, FieldType<?>> types =
        new HashMap<Integer, FieldType<?>>();
    private final DataSource dataSource;
    DatabaseAdapter(final DataSource tdataSource) {
        this.dataSource = tdataSource;
    }
    public final void registerTypes() {
        types.put(Types.BIGINT, LichenArTypes.BIG_INTEGER);
        types.put(Types.BINARY, LichenArTypes.BINARY);
        types.put(Types.BIT, LichenArTypes.BIT);
        types.put(Types.CHAR, LichenArTypes.CHAR);
        types.put(Types.DATE, LichenArTypes.DATE);
        types.put(Types.DOUBLE, LichenArTypes.DOUBLE);
        types.put(Types.FLOAT, LichenArTypes.FLOAT);
        types.put(Types.INTEGER, LichenArTypes.INTEGER);
        types.put(Types.SMALLINT, LichenArTypes.SMALLINT);
        types.put(Types.TINYINT, LichenArTypes.TINYINT);
        types.put(Types.TIME, LichenArTypes.TIME);
        types.put(Types.TIMESTAMP, LichenArTypes.TIMESTAMP);
        types.put(Types.VARCHAR, LichenArTypes.VARCHAR);
        types.put(Types.VARBINARY, LichenArTypes.VARBINARY);
        types.put(Types.NUMERIC, LichenArTypes.NUMERIC);
        types.put(Types.DECIMAL, LichenArTypes.DECIMAL);
        types.put(Types.BLOB, LichenArTypes.BLOB);
        types.put(Types.CLOB, LichenArTypes.CLOB);
    }

    final JdbcHelper createJdbcHelper() {
        return new JdbcHelperImpl(dataSource);
    }
}
