/*		
 * Copyright 2013-5-6 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,ClientCommondTest.java,fangj Exp$
 * created at:下午01:40:32
 */
package com.egf.db.command.support;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.egf.db.services.impl.AbstractMigration;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class ClientCommondTest {

	Logger logger=Logger.getLogger(ClientCommondTest.class);
	/**
	 * Test method for {@link com.egf.db.command.support.ClientCommond#up()}.
	 */
	@Test
	public void testUp() throws Exception{
			Enhancer en= new Enhancer();
			Class c= Class.forName("com.egf.db.migration.CreateTable_201305021002");
			en.setSuperclass(c);
			en.setCallback(new MethodInterceptor() {
				public Object intercept(Object obj, Method method, Object[] args,
						MethodProxy proxy) throws Throwable {
					return proxy.invokeSuper(obj, args);
				}
			});
			AbstractMigration am=(AbstractMigration)en.create();
			am.up();
	}

}
