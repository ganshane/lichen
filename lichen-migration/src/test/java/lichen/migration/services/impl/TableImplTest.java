/*		
 * Copyright 2013-5-27 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,TableImplTest.java,fangj Exp$
 * created at:上午09:26:50
 */
package lichen.migration.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class TableImplTest {

	TableImpl table=new TableImpl();
	
	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#blob(java.lang.String, lichen.migration.core.define.column.Comment)}.
	 */
	@Test
	public void testBlobStringComment() {
		table.blob("pic", new CommentImpl("图片"));
		assertEquals("pic blob,\n", table.columns.toString());
		assertEquals("comment on column TN.pic is '图片';\n", table.comments.toString());
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#blob(java.lang.String, lichen.migration.core.define.column.NotNull, lichen.migration.core.define.column.Comment)}.
	 */
	@Test
	public void testBlobStringNotNullComment() {
		table.blob("pic", new NotNullImpl(), new CommentImpl("图片"));
		assertEquals("pic blob not null,\n", table.columns.toString());
		assertEquals("comment on column TN.pic is '图片';\n", table.comments.toString());
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#blob(java.lang.String, lichen.migration.core.define.column.NotNull)}.
	 */
	@Test
	public void testBlobStringNotNull() {
		table.blob("pic", new NotNullImpl());
		assertEquals("pic blob not null,\n", table.columns.toString());
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#blob(java.lang.String)}.
	 */
	@Test
	public void testBlobString() {
		table.blob("pic");
		assertEquals("pic blob,\n", table.columns.toString());
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#number(java.lang.String, lichen.migration.core.define.column.Comment)}.
	 */
	@Test
	public void testNumberStringComment() {
		table.integer("age",new CommentImpl("年龄"));
		assertEquals("age number,\n", table.columns.toString());
		assertEquals("comment on column TN.age is '年龄';\n", table.comments.toString());
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#number(java.lang.String, lichen.migration.core.define.column.Default, lichen.migration.core.define.column.Comment)}.
	 */
	@Test
	public void testNumberStringDefaultComment() {
		table.integer("age", new DefaultImpl("30"), new CommentImpl("年龄"));
		assertEquals("age number default 30,\n", table.columns.toString());
		assertEquals("comment on column TN.age is '年龄';\n", table.comments.toString());
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#number(java.lang.String, lichen.migration.core.define.column.Default, lichen.migration.core.define.column.NotNull, lichen.migration.core.define.column.Comment)}.
	 */
	@Test
	public void testNumberStringDefaultNotNullComment() {
		table.integer("age", new NotNullImpl(), new DefaultImpl("30"),new CommentImpl("年龄"));
		assertEquals("age number not null default 30,\n", table.columns.toString());
		assertEquals("comment on column TN.age is '年龄';\n", table.comments.toString());
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#number(java.lang.String, lichen.migration.core.define.column.ColumnDefine[])}.
	 */
	@Test
	public void testNumberStringColumnDefineArray() {
		table.integer("age", new NotNullImpl(),new DefaultImpl("30"), new CommentImpl("年龄"));
		assertEquals("age number not null default 30,\n", table.columns.toString());
		assertEquals("comment on column TN.age is '年龄';\n", table.comments.toString());
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#number(java.lang.String, lichen.migration.core.define.column.Default, lichen.migration.core.define.column.NotNull)}.
	 */
	@Test
	public void testNumberStringDefaultNotNull() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#number(java.lang.String, lichen.migration.core.define.column.Default)}.
	 */
	@Test
	public void testNumberStringDefault() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#number(java.lang.String, lichen.migration.core.define.column.NotNull, lichen.migration.core.define.column.Comment)}.
	 */
	@Test
	public void testNumberStringNotNullComment() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#number(java.lang.String, lichen.migration.core.define.column.NotNull)}.
	 */
	@Test
	public void testNumberStringNotNull() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#number(java.lang.String)}.
	 */
	@Test
	public void testNumberString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#varchar2(java.lang.String, lichen.migration.core.define.column.Limit, lichen.migration.core.define.column.Comment)}.
	 */
	@Test
	public void testVarchar2StringLimitComment() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#varchar2(java.lang.String, lichen.migration.core.define.column.Limit, lichen.migration.core.define.column.Default, lichen.migration.core.define.column.Comment)}.
	 */
	@Test
	public void testVarchar2StringLimitDefaultComment() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#varchar2(java.lang.String, lichen.migration.core.define.column.Limit, lichen.migration.core.define.column.Default, lichen.migration.core.define.column.NotNull, lichen.migration.core.define.column.Comment)}.
	 */
	@Test
	public void testVarchar2StringLimitDefaultNotNullComment() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#varchar2(java.lang.String, lichen.migration.core.define.column.Limit, lichen.migration.core.define.column.Default, lichen.migration.core.define.column.NotNull)}.
	 */
	@Test
	public void testVarchar2StringLimitDefaultNotNull() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#varchar2(java.lang.String, lichen.migration.core.define.column.Limit, lichen.migration.core.define.column.Default)}.
	 */
	@Test
	public void testVarchar2StringLimitDefault() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#varchar2(java.lang.String, lichen.migration.core.define.column.Limit, lichen.migration.core.define.column.NotNull, lichen.migration.core.define.column.Comment)}.
	 */
	@Test
	public void testVarchar2StringLimitNotNullComment() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#varchar2(java.lang.String, lichen.migration.core.define.column.Limit, lichen.migration.core.define.column.NotNull)}.
	 */
	@Test
	public void testVarchar2StringLimitNotNull() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#varchar2(java.lang.String, lichen.migration.core.define.column.Limit)}.
	 */
	@Test
	public void testVarchar2StringLimit() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link lichen.migration.services.impl.TableImpl#varchar2(java.lang.String, lichen.migration.core.define.column.Limit, lichen.migration.core.define.column.ColumnDefine[])}.
	 */
	@Test
	public void testVarchar2StringLimitColumnDefineArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testClobStringColumnDefineArray(){
		table.clob("clob", new CommentImpl("clob"));
		assertEquals("clob clob,\n", table.columns.toString());
		assertEquals("comment on column TN.clob is 'clob';\n", table.comments.toString());
	}
	
	@Test
	public void testClobStringNotNull(){
		table.clob("clob",new NotNullImpl());
		assertEquals("clob clob not null,\n", table.columns.toString());
	}
	
	@Test
	public void testClobStringNotNullComment(){
		table.clob("clob",new NotNullImpl(),new CommentImpl("test clob"));
		assertEquals("clob clob not null,\n", table.columns.toString());
		assertEquals("comment on column TN.clob is 'test clob';\n", table.comments.toString());
	}
	
	@Test
	public void testClobStringComment(){
		table.clob("clob",new CommentImpl("test clob"));
		assertEquals("clob clob,\n", table.columns.toString());
		assertEquals("comment on column TN.clob is 'test clob';\n", table.comments.toString());
	}
	
	
	@Test
	public void testDateStringColumnDefineArray(){
		table.date("rksj", new CommentImpl("入库时间"));
		assertEquals("rksj date,\n", table.columns.toString());
		assertEquals("comment on column TN.rksj is '入库时间';\n", table.comments.toString());
	}
	
	@Test
	public void testDateStringNotNull(){
		table.date("rksj", new CommentImpl("入库时间"));
		assertEquals("rksj date,\n", table.columns.toString());
		assertEquals("comment on column TN.rksj is '入库时间';\n", table.comments.toString());
	}
	
	@Test
	public void testDateStringNotNullComment(){
		table.date("rksj",new NotNullImpl(),new CommentImpl("入库时间"));
		assertEquals("rksj date not null,\n", table.columns.toString());
		assertEquals("comment on column TN.rksj is '入库时间';\n", table.comments.toString());
	}
	
	@Test
	public void testDateStringComment(){
		table.date("rksj",new CommentImpl("入库时间"));
		assertEquals("rksj date,\n", table.columns.toString());
		assertEquals("comment on column TN.rksj is '入库时间';\n", table.comments.toString());
	}
	
}
