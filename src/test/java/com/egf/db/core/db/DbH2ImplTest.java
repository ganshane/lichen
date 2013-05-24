/*		
 * Copyright 2013-5-24 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DbH2ImplTest.java,fangj Exp$
 * created at:下午01:31:16
 */
package com.egf.db.core.db;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class DbH2ImplTest {

	/**
	 * Test method for {@link com.egf.db.core.db.DbH2Impl#getPrimaryKeyColumn(java.lang.String)}.
	 */
	@Test
	public void testGetPrimaryKeyColumn() {
		assertArrayEquals(new String[]{"ID"},DbFactory.getDb().getPrimaryKeyColumn("test"));
	}

	/**
	 * Test method for {@link com.egf.db.core.db.DbH2Impl#getPrimaryKeyName(java.lang.String)}.
	 */
	@Test
	public void testGetPrimaryKeyName() {
		assertEquals("PK",DbFactory.getDb().getPrimaryKeyName("test"));
	}

}
