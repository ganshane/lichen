/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,JdbcServiceImplTest.java,fangj Exp$
 * created at:下午03:57:26
 */
package com.egf.db.core.jdbc;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author fangj
 * @version $Revision:  $
 * @since 1.0
 */
public class JdbcServiceImplTest {

	Logger logger=Logger.getLogger(JdbcServiceImplTest.class);
	/**
	 * Test method for {@link com.egf.db.core.jdbc.JdbcServiceImpl#execute(java.lang.String)}.
	 */
	@Test
	public void testExecuteString() {
		JdbcService jdbcService= new JdbcServiceImpl();
		logger.debug(jdbcService);
	}

}
