/*		
 * Copyright 2013-5-17 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,FileUtilsTest.java,fangj Exp$
 * created at:上午09:10:40
 */
package com.egf.db.utils;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class FileUtilsTest {
	
	Logger logger=Logger.getLogger(FileUtilsTest.class);

	/**
	 * Test method for {@link com.egf.db.utils.FileUtils#getClasses(java.lang.String)}.
	 */
	@Test
	public void testGetClasses() {
		Set<Class<?>> set=FileUtils.getDbScriptClasses("com.egf.db.migration");
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Class<?> class1 = (Class<?>) iterator.next();
			logger.debug(">>"+class1.getName());
		}
		
	}

}
