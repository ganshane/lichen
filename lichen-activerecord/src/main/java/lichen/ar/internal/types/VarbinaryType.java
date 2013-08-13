// Copyright 2013 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
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
 * 映射数据库中的{@link java.sql.Types#VARBINARY} 成 {@link byte[]} 对象
 * @author weiweng
 *
 */
public class VarbinaryType implements FieldType<byte[]> {

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