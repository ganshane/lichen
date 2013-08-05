package lichen.ar.internal;

import lichen.ar.services.FieldType;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * 针对不通数据库的适配器
 * @author jcai
 */
public class DatabaseAdapter {
    private final Map<Integer,FieldType<?>> types = new HashMap<Integer,FieldType<?>>();
    public void registerTypes(){
        types.put( Types.BIGINT, LichenArTypes.BIG_INTEGER);
        /*
        types.put( Types.BINARY, Types.BINARY.getName() );
        types.put( Types.BIT, Types.BOOLEAN.getName() );
        types.put( Types.CHAR, Types.CHARACTER.getName() );
        types.put( Types.DATE, Types.DATE.getName() );
        types.put( Types.DOUBLE, Types.DOUBLE.getName() );
        types.put( Types.FLOAT, Types.FLOAT.getName() );
        types.put( Types.INTEGER, Types.INTEGER.getName() );
        types.put( Types.SMALLINT, Types.SHORT.getName() );
        types.put( Types.TINYINT, Types.BYTE.getName() );
        types.put( Types.TIME, Types.TIME.getName() );
        types.put( Types.TIMESTAMP, Types.TIMESTAMP.getName() );
        types.put( Types.VARCHAR, Types.STRING.getName() );
        types.put( Types.VARBINARY, Types.BINARY.getName() );
        types.put( Types.NUMERIC, Types.BIG_DECIMAL.getName() );
        types.put( Types.DECIMAL, Types.BIG_DECIMAL.getName() );
        types.put( Types.BLOB, Types.BLOB.getName() );
        types.put( Types.CLOB, Types.CLOB.getName() );
        types.put( Types.REAL, Types.FLOAT.getName() );
        */
    }
}
