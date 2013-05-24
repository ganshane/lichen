/*		
 * Copyright 2013-5-24 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DbOracleImplTest.java,fangj Exp$
 * created at:下午05:17:19
 */
package com.egf.db.core.db;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class DbOracleImplTest {

	
	@Test
	public void testGetPrimaryKeyColumn() {
		assertArrayEquals(new String[]{"ID", "BB"}, DbFactory.getDb().getPrimaryKeyColumn("zdry.test"));
	}

	
	@Test
	public void testGetPrimaryKeyName() {
		assertEquals("PK", DbFactory.getDb().getPrimaryKeyName("zdry.test"));
	}

}
