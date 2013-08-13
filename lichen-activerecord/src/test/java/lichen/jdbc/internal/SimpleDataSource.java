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
package lichen.jdbc.internal;

import lichen.core.services.LichenException;
import lichen.jdbc.services.JdbcErrorCode;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.io.PrintWriter;

/**
 * This is a simplest implementation of a java.sql.DataSource interface.
 * It is highly recommended to use another datasource implementation from your
 * database or application server vendor instead of this one.
 *
 * This class is only a small utility.
 *
 * @author Erdinc YILMAZEL
 */
public class SimpleDataSource implements DataSource {

   public SimpleDataSource(String driverClassName, String url, String username, String password) {
      this.url = url;
      this.username = username;
      this.password = password;

      try {
         Class.forName(driverClassName.trim());
      } catch (ClassNotFoundException e) {
          throw LichenException.wrap(e, JdbcErrorCode.DRIVER_NOT_FOUND);
      }
   }

   private String url;

	private String username;

	private String password;


   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Connection getConnection() throws SQLException {
      return DriverManager.getConnection(url, username, password);
   }

   public Connection getConnection(String username, String password) throws SQLException {
      this.username = username;
      this.password = password;
      return DriverManager.getConnection(url, username, password);
   }

   public PrintWriter getLogWriter() throws SQLException {
      return null;
   }

   public void setLogWriter(PrintWriter out) throws SQLException {

   }

   public void setLoginTimeout(int seconds) throws SQLException {
   }

   public int getLoginTimeout() throws SQLException {
      return 0;
   }

   @SuppressWarnings("unchecked")
   public Object unwrap(Class iface) throws SQLException {
		if (!DataSource.class.equals(iface)) {
			throw new SQLException("DataSource of type [" + getClass().getName() +
					"] can only be unwrapped as [javax.sql.DataSource], not as [" + iface.getName());
		}
		return this;
	}

	public boolean isWrapperFor(Class iface) throws SQLException {
		return DataSource.class.equals(iface);
	}
}
