/*		
 * Copyright 2013-5-6 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DatabaseServiceImplTest.java,fangj Exp$
 * created at:下午03:17:42
 */
package com.egf.db.services.impl;

import java.sql.SQLException;

import org.junit.Test;
import org.mockito.Mockito;

import com.egf.db.core.CreateTableCallback;
import com.egf.db.core.jdbc.JdbcService;
import com.egf.db.core.jdbc.JdbcServiceImpl;
import com.egf.db.core.model.Table;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class DatabaseServiceImplTest {
	
	DatabaseServiceImpl service=new DatabaseServiceImpl();
	
	JdbcService js=new JdbcServiceImpl();
	
	JdbcService jdbcService = Mockito.mock(JdbcService.class);

	/**
	 * Test method for {@link com.egf.db.services.impl.DatabaseServiceImpl#addColumn(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.ColumnType, com.egf.db.core.define.column.ColumnDefine[])}.
	 */
	@Test
	public void testCreateTable() throws SQLException{
		service.setJdbcService(jdbcService);
		service.createTable(new TableNameImpl("zdry.test"),new CommentImpl("表注释"), new CreateTableCallback() {
			public void doCreateAction(Table t) {
				t.number("id");
				t.varchar2("xm",new LimitImpl(10),new NotNullImpl(),new CommentImpl("dd"));
				t.blob("pic");
				t.number("age",new NotNullImpl());
				t.varchar2("xx",new LimitImpl(20),new CommentImpl("dd"));
			}
		});
		StringBuffer sb=new StringBuffer();
		sb.append("create table zdry.test (\n");
		sb.append("id number,\n");
		sb.append("xm varchar2(10) not null,\n");
		sb.append("pic blob,\n");
		sb.append("age number not null,\n");
		sb.append("xx varchar2(20)\n");
		sb.append(");\n");
		sb.append("comment on zdry.table test is '表注释';\n");
		sb.append("comment on column zdry.test.xm is 'dd';\n");
		sb.append("comment on column zdry.test.xx is 'dd';\n");
		Mockito.verify(jdbcService).execute(sb.toString());
	}
	
	@Test
	public void testCreateTable1() throws SQLException{
		service.setJdbcService(jdbcService);
		service.createTable(new TableNameImpl("test"),new CommentImpl("表注释"), new CreateTableCallback() {
			public void doCreateAction(Table t) {
				t.number("id");
				t.varchar2("xm",new LimitImpl(10),new NotNullImpl(),new CommentImpl("dd"));
				t.blob("pic");
				t.number("age",new NotNullImpl());
				t.varchar2("xx",new LimitImpl(20),new CommentImpl("dd"));
			}
		});
		StringBuffer sb=new StringBuffer();
		sb.append("create table test (\n");
		sb.append("id number,\n");
		sb.append("xm varchar2(10) not null,\n");
		sb.append("pic blob,\n");
		sb.append("age number not null,\n");
		sb.append("xx varchar2(20)\n");
		sb.append(");\n");
		sb.append("comment on table test is '表注释';\n");
		sb.append("comment on column test.xm is 'dd';\n");
		sb.append("comment on column test.xx is 'dd';\n");
		Mockito.verify(jdbcService).execute(sb.toString());
	}
	
	@Test
	public void testCreateTable2() throws SQLException{
		service.setJdbcService(jdbcService);
		service.createTable(new TableNameImpl("test2"), new CreateTableCallback() {
			public void doCreateAction(Table t) {
				t.number("id");
				t.varchar2("xm",new LimitImpl(10),new NotNullImpl(),new CommentImpl("dd"));
				t.blob("pic");
				t.number("age",new NotNullImpl());
				t.varchar2("xx",new LimitImpl(20),new CommentImpl("dd"));
			}
		});
		StringBuffer sb=new StringBuffer();
		sb.append("create table test2 (\n");
		sb.append("id number,\n");
		sb.append("xm varchar2(10) not null,\n");
		sb.append("pic blob,\n");
		sb.append("age number not null,\n");
		sb.append("xx varchar2(20)\n");
		sb.append(");\n");
		sb.append("comment on column test2.xm is 'dd';\n");
		sb.append("comment on column test2.xx is 'dd';\n");
		Mockito.verify(jdbcService).execute(sb.toString());
	}
	
	@Test
	public void testAddColumn() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("test"), new Varchar2Impl(2));
		Mockito.verify(jdbcService).execute("alter table test add test varchar2(2);");
	}

	@Test
	public void testAddColumn2() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("sex"), new Varchar2Impl(2),new CommentImpl("性别"));
		Mockito.verify(jdbcService).execute("alter table test add sex varchar2(2);\ncomment on column test.sex is '性别';");
	}
	
	@Test
	public void testAddColumn3() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("default"), new Varchar2Impl(8),new CommentImpl("性别"),new DefaultImpl("默认值"));
		Mockito.verify(jdbcService).execute("alter table test add default varchar2(8) default '默认值';\ncomment on column test.default is '性别';");
	}
	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefault() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("aa"), new Varchar2Impl(2), new CommentImpl("test"));
		Mockito.verify(jdbcService).execute("alter table test add aa varchar2(2);\ncomment on column test.aa is 'test';");
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeNullOrNotNull() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("bb"), new Varchar2Impl(4),  new DefaultImpl("test"));
		Mockito.verify(jdbcService).execute("alter table test add bb varchar2(4) default 'test';");
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefaultNullOrNotNull() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("cc"), new Varchar2Impl(2), new NotNullImpl());
		Mockito.verify(jdbcService).execute("alter table test add cc varchar2(2) not null;");
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeComment() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("dd"), new Varchar2Impl(4),  new DefaultImpl("test"), new CommentImpl("test"));
		Mockito.verify(jdbcService).execute("alter table test add dd varchar2(4) default 'test';\ncomment on column test.dd is 'test';");
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefaultComment() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("ee"), new Varchar2Impl(4),new DefaultImpl("test"), new NotNullImpl());
		Mockito.verify(jdbcService).execute("alter table test add ee varchar2(4) default 'test' not null;");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addColumn(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.ColumnType, com.egf.db.core.define.column.NullOrNotNull, com.egf.db.core.define.column.Comment)}.
	 */
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeNullOrNotNullComment() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("ff"), new Varchar2Impl(4),  new NotNullImpl(), new CommentImpl("test"));
		Mockito.verify(jdbcService).execute("alter table test add ff varchar2(4) not null;\ncomment on column test.ff is 'test';");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addColumn(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.ColumnType, com.egf.db.core.define.column.Default, com.egf.db.core.define.column.NullOrNotNull, com.egf.db.core.define.column.Comment)}.
	 */
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefaultNullOrNotNullComment() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("gg"), new Varchar2Impl(4), new DefaultImpl("test"), new NotNullImpl(), new CommentImpl("test"));
		Mockito.verify(jdbcService).execute("alter table test add gg varchar2(4) default 'test' not null;\ncomment on column test.gg is 'test';");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addComment(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.column.Comment)}.
	 */
	@Test
	public void testAddComment() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addComment(new TableNameImpl("test"), new ColumnNameImpl("aa"), new CommentImpl("test"));
		Mockito.verify(jdbcService).execute("comment on column test.aa is 'test';");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#updateComment(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.column.Comment)}.
	 */
	@Test
	public void testUpdateComment() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addComment(new TableNameImpl("test"), new ColumnNameImpl("bb"), new CommentImpl("aa"));
		Mockito.verify(jdbcService).execute("comment on column test.bb is 'aa';");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addDefault(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.column.Default)}.
	 */
	@Test
	public void testAddDefault() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addDefault(new TableNameImpl("test"), new ColumnNameImpl("cc"), new DefaultImpl("test"));
		Mockito.verify(jdbcService).execute("alter table test modify cc null default 'test';");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#updateDefault(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.column.Default)}.
	 */
	@Test
	public void testUpdateDefault() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addDefault(new TableNameImpl("test"), new ColumnNameImpl("xx"), new DefaultImpl("dddd"));
		Mockito.verify(jdbcService).execute("alter table test modify xx null default 'dddd';");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addIndex(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.IndexName, com.egf.db.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddIndexTableNameIndexNameColumnNameArray()throws SQLException {
		service.setJdbcService(jdbcService);
		service.addIndex(new TableNameImpl("test"), new IndexNameImpl("index"), new ColumnNameImpl("aa"),new ColumnNameImpl("dd"));
		Mockito.verify(jdbcService).execute("create index index on test (aa,dd);");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addIndex(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.IndexName, com.egf.db.core.define.IndexType, com.egf.db.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddIndexTableNameIndexNameIndexTypeColumnNameArray() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addIndex(new TableNameImpl("test"), new IndexNameImpl("index2"), new IndexUniqueImpl(), new ColumnNameImpl("bb"));
		Mockito.verify(jdbcService).execute("create unique index index2 on test (bb);");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addPrimaryKey(java.lang.String, com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddPrimaryKey() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addPrimaryKey("pk", new TableNameImpl("zdry.test"), new ColumnNameImpl("id"));
		Mockito.verify(jdbcService).execute("alter table zdry.test add constraint pk primary key (id)");
	}


	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addUnique(java.lang.String, com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddUnique() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addUnique("u1", new TableNameImpl("test"), new ColumnNameImpl("xx"));
		Mockito.verify(jdbcService).execute("alter table test add constraint u1 unique (xx);");
	}

	

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropIndex(java.lang.String)}.
	 */
	@Test
	public void testDropIndex() throws SQLException{
		service.setJdbcService(jdbcService);
		service.dropIndex("index2");
		Mockito.verify(jdbcService).execute("drop index index2;");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropColumn(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName)}.
	 */
	@Test
	public void testDropColumn() throws SQLException{
		service.setJdbcService(jdbcService);
		service.dropColumn(new TableNameImpl("test"), new ColumnNameImpl("age"));
		Mockito.verify(jdbcService).execute("alter table test drop column age;");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropPrimaryKey(com.egf.db.core.define.name.TableName, java.lang.String)}.
	 */
	@Test
	public void testDropPrimaryKey() throws SQLException{
		service.setJdbcService(jdbcService);
		service.dropPrimaryKey(new TableNameImpl("zdry.test"));
		Mockito.verify(jdbcService).execute("alter table zdry.test drop constraint PK;");
	}

	
	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropUnique(com.egf.db.core.define.name.TableName, java.lang.String)}.
	 */
	@Test
	public void testDropUnique() throws SQLException{
		service.setJdbcService(jdbcService);
		service.dropUnique(new TableNameImpl("test"), "u1");
		Mockito.verify(jdbcService).execute("alter table test drop constraint u1;");
	}
	
	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropTable(java.lang.String)}.
	 */
	@Test
	public void testDropTable() throws SQLException{
		service.setJdbcService(jdbcService);
		service.dropTable("test");
		Mockito.verify(jdbcService).execute("drop table test;");
	}
}
