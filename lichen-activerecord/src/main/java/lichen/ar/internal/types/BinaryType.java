package lichen.ar.internal.types;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lichen.ar.services.FieldType;

/**
 * 映射数据库中的{@link java.sql.Types#BINARY} 成 {@link byte[]} 对象
 * @author weiweng
 *
 */
public class BinaryType implements FieldType<byte[]> {

	public static int BYTEARR_SIZE = 1024;

	@Override
	public byte[] get(ResultSet rs, int index) throws SQLException {
		InputStream is = rs.getBinaryStream(index);
		ByteArrayOutputStream bos = null;
		try {
			List<byte[]> list = new ArrayList<byte[]>();
			byte[] b = new byte[BYTEARR_SIZE];
			int len = 0;
			try {
				while((len = is.read(b)) != -1) {
					if(BYTEARR_SIZE > len) {
						byte[] mi = new byte[len];
						for(int i = 0; i < len; i++) {
							mi[i] = b[i];
						}
						list.add(mi);
					} else {
						list.add(b.clone());
					}
				}
				bos = new ByteArrayOutputStream();
				for(byte[] t : list) {
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