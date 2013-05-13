/*		
 * Copyright 2013-5-13 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,GenerateImplTest.java,fangj Exp$
 * created at:下午04:34:14
 */
package com.egf.db.services.impl;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.egf.db.core.sql.template.Generate;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class GenerateImplTest {

	Logger logger=Logger.getLogger(GenerateImplTest.class);
	
	Generate generate =new GenerateImpl();
	/**
	 * Test method for {@link com.egf.db.services.impl.GenerateImpl#AddConstraint(java.lang.String, java.lang.String, java.lang.String, java.lang.String[])}.
	 */
	@Test
	public void testAddConstraint() {
		String sql=generate.AddConstraint("dtxx.test", "aa", "unique", "cl1","cl2");
		assertEquals("alter table dtxx.test add constraint aa unique (cl1,cl2);", sql);
		
		String sql2=generate.AddConstraint("test", "aa", "unique", "cl1");
		assertEquals("alter table test add constraint aa unique (cl1);", sql2);
	}

	/**
	 * Test method for {@link com.egf.db.services.impl.GenerateImpl#addIndex(java.lang.String, java.lang.String, java.lang.String[])}.
	 */
	@Test
	public void testAddIndexStringStringStringArray() {
		String sql=generate.addIndex("zdry.zdryqbxx", "test", new String[]{"cl1","cl2"});
		assertEquals("create index test on zdry.zdryqbxx (cl1,cl2);", sql);
		
		String sql2=generate.addIndex("zdry.zdryqbxx", "test", new String[]{"cl1"});
		assertEquals("create index test on zdry.zdryqbxx (cl1);", sql2);
	}

	/**
	 * Test method for {@link com.egf.db.services.impl.GenerateImpl#addIndex(java.lang.String, java.lang.String, java.lang.String, java.lang.String[])}.
	 */
	@Test
	public void testAddIndexStringStringStringStringArray() {
		String sql=generate.addIndex("zdry.zdryqbxx", "test", "unique", new String[]{"cl1","cl2"});
		assertEquals("create unique index test on zdry.zdryqbxx (cl1,cl2);", sql);
	}

	/**
	 * Test method for {@link com.egf.db.services.impl.GenerateImpl#AddColumn(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddColumnStringStringString() {
		String sql=generate.AddColumn("test", "cl1", "varchar2(10)");
		assertEquals("alter table test add cl1 varchar2(10);", sql);
	}

	/**
	 * Test method for {@link com.egf.db.services.impl.GenerateImpl#AddColumn(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddColumnStringStringStringString() {
		String sql=generate.AddColumn("test", "cl1", "number", "not null");
		assertEquals("alter table test add cl1 number not null;", sql);
		
		String sql2=generate.AddColumn("test", "cl1", "number");
		assertEquals("alter table test add cl1 number;", sql2);
		
		
	}

	/**
	 * Test method for {@link com.egf.db.services.impl.GenerateImpl#addComment(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddComment() {
		String sql=generate.addComment("test", "cl1", "注释");
		assertEquals("comment on column test.cl1 is '注释';", sql);
	}

	/**
	 * Test method for {@link com.egf.db.services.impl.GenerateImpl#dropColumn(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testDropColumn() {
		String sql=generate.dropColumn("test", "cl1");
		assertEquals("alter table test drop column cl1;", sql);
	}

	/**
	 * Test method for {@link com.egf.db.services.impl.GenerateImpl#dropIndex(java.lang.String)}.
	 */
	@Test
	public void testDropIndex() {
		String sql=generate.dropIndex("tt");
		assertEquals("drop index tt;", sql);
	}

	/**
	 * Test method for {@link com.egf.db.services.impl.GenerateImpl#dropTalbe(java.lang.String)}.
	 */
	@Test
	public void testDropTalbe() {
		String sql=generate.dropTalbe("tt");
		assertEquals("drop table tt;", sql);
	}

	/**
	 * Test method for {@link com.egf.db.services.impl.GenerateImpl#dropConstraint(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testDropConstraint() {
		String sql=generate.dropConstraint("test", "test");
		assertEquals("alter table test drop constraint test;", sql);
	}

}
