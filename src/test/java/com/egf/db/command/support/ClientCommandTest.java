/*		
 * Copyright 2013-5-6 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,ClientCommondTest.java,fangj Exp$
 * created at:下午01:40:32
 */
package com.egf.db.command.support;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.egf.db.command.Command;
import com.egf.db.core.jdbc.JdbcService;
import com.egf.db.core.jdbc.JdbcServiceImpl;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class ClientCommandTest {

	Logger logger=Logger.getLogger(ClientCommandTest.class);
	
	JdbcService jdbcService=new JdbcServiceImpl();
	
	/**
	 * Test method for {@link com.egf.db.command.support.ClientCommand#up()}.
	 */
	@Test
	public void testUp() throws Exception{
		Command command=new ClientCommand();
		command.up("com.egf.db.migration");
		//获取连接对象
		Class<?> cl= Class.forName("com.egf.db.services.impl.DatabaseServiceImpl");
		Field field = cl.getDeclaredField("connection");
		field.setAccessible(true);
		Connection connection =  (Connection) field.get(cl);
		jdbcService.execute("insert into test(id,xm,xx) values(1,'aa','121')", connection);
		connection.commit();
		//查询
		List<Object[]>  list=jdbcService.find("select * from test");
		logger.debug("list 大小:"+list.size());
	}

}
