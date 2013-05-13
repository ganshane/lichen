/*		
 * Copyright 2013-5-7 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,CreateIndexImplTest.java,fangj Exp$
 * created at:下午12:57:13
 */
package com.egf.db.services.generate.impl;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.egf.db.core.sql.template.Generate;
import com.egf.db.services.impl.GenerateImpl;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class CreateIndexImplTest {

	Logger logger=Logger.getLogger(CreateIndexImplTest.class);
	
	@Test
	public void testGenerateSql() {
		Generate service=new GenerateImpl();
		String s=service.addIndex("test", "a", new String[]{"dd"});
		logger.debug(s+">>>");
	}
	
	@Test
	public void testGenerateSql2() {
		Generate service=new GenerateImpl();
		String s=service.addIndex("test","test","unique", new String[]{"dd"});
		logger.debug(s+">>>");
	}
}
