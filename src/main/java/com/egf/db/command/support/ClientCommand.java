/*		
 * Copyright 2013-5-3 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,ClientCommond.java,fangj Exp$
 * created at:下午05:09:26
 */
package com.egf.db.command.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Set;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.log4j.Logger;

import com.egf.db.command.Command;
import com.egf.db.core.jdbc.JdbcService;
import com.egf.db.core.jdbc.JdbcServiceImpl;
import com.egf.db.services.impl.AbstractMigration;
import com.egf.db.utils.FileUtils;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class ClientCommand implements Command{
	
	Logger logger=Logger.getLogger(ClientCommand.class);
	
	JdbcService jdbcService=new JdbcServiceImpl();
	
	public void up(String pack) {
		Enhancer en= new Enhancer();
		Set<Class<?>> classList=FileUtils.getDbScriptClasses(pack);
		for (Class cls : classList) {
			try {
				en.setSuperclass(cls);
				en.setCallback(new MethodInterceptor() {
					public Object intercept(Object obj, Method method, Object[] args,MethodProxy proxy) throws Throwable {
						return proxy.invokeSuper(obj, args);
					}
				});
				AbstractMigration am=(AbstractMigration)en.create();
				am.up();
				//获取连接对象
				Class<?> cl= Class.forName("com.egf.db.services.impl.DatabaseServiceImpl");
				Field field = cl.getDeclaredField("connection");
				field.setAccessible(true);
				Connection connection =  (Connection) field.get(cl);
				connection.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void down(String version) {
		// TODO Auto-generated method stub
		
	}

	
	

}
