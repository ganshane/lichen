package lichen.ar.internal.types;

import lichen.ar.services.FieldType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * binary抽象类.
 *
 * @author Administrator
 */
public class BinaryType implements FieldType<byte[]> {

    /**
     * 字节数组的大小.
     */
    public static final int BYTEARRSIZE = 1024;

    /**
     * get方法.
     *
     * @param rs    结果集
     * @param index 列
     * @return 字节数组
     * @throws SQLException 异常
     */
    @Override
    public byte[] get(ResultSet rs, int index)
            throws SQLException {
        InputStream is = rs.getBinaryStream(index);
        ByteArrayOutputStream bos = null;
        try {
            List<byte[]> list = new ArrayList<byte[]>();
            byte[] b = new byte[BYTEARRSIZE];
            int len = 0;
            try {
                while ((len = is.read(b)) != -1) {
                    if (BYTEARRSIZE > len) {
                        byte[] mi = new byte[len];
                        for (int i = 0; i < len; i++) {
                            mi[i] = b[i];
                        }
                        list.add(mi);
                    } else {
                        list.add(b.clone());
                    }
                }
                bos = new ByteArrayOutputStream();
                for (byte[] t : list) {
                    bos.write(t);
                }
                bos.flush();
                return bos.toByteArray();
            } finally {
                bos.close();
                is.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void set(PreparedStatement ps, int index, byte[] object)
            throws SQLException {
        ps.setBinaryStream(index, new ByteArrayInputStream(object));
    }

}
