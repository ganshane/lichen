/*		
 * Copyright 2013-5-6 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DatabaseServiceImplTest.java,fangj Exp$
 * created at:下午03:17:42
 */
package lichen.migration.services.impl;

import java.sql.SQLException;

import org.junit.Test;
import org.mockito.Mockito;

import lichen.migration.core.CreateTableCallback;
import lichen.migration.core.jdbc.JdbcService;
import lichen.migration.core.jdbc.JdbcServiceImpl;
import lichen.migration.core.model.Table;

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
	 * Test method for {@link lichen.migration.services.impl.DatabaseServiceImpl#addColumn(lichen.migration.core.define.name.TableName, lichen.migration.core.define.name.ColumnName, lichen.migration.core.define.ColumnType, lichen.migration.core.define.column.ColumnDefine[])}.
	 */
	@Test
	public void testCreateTable() throws SQLException{
		service.setJdbcService(jdbcService);
		service.createTable(new TableNameImpl("zdry.test"),new CommentImpl("表注释"), new CreateTableCallback() {
			public void doCreateAction(Table t) {
				t.integer("id",new PrimaryKeyImpl());
				t.String("xm",new LimitImpl(10),new NotNullImpl(),new CommentImpl("dd"));
				t.blob("pic");
				t.integer("age",new NotNullImpl());
				t.String("xx",new LimitImpl(20),new CommentImpl("dd"));
			}
		});
		StringBuffer sb=new StringBuffer();
		sb.append("create table zdry.test (\n");
		sb.append("id number primary key,\n");
		sb.append("xm varchar2(10) not null,\n");
		sb.append("pic blob,\n");
		sb.append("age number not null,\n");
		sb.append("xx varchar2(20)\n");
		sb.append(");\n");
		sb.append("comment on table zdry.test is '表注释';\n");
		sb.append("comment on column zdry.test.xm is 'dd';\n");
		sb.append("comment on column zdry.test.xx is 'dd';\n");
		Mockito.verify(jdbcService).execute(sb.toString());
	}
	
	@Test
	public void testCreateTable1() throws SQLException{
		service.setJdbcService(jdbcService);
		service.createTable(new TableNameImpl("test"),new CommentImpl("表注释"), new CreateTableCallback() {
			public void doCreateAction(Table t) {
				t.integer("id");
				t.String("xm",new LimitImpl(10),new NotNullImpl(),new CommentImpl("dd"));
				t.blob("pic");
				t.integer("age",new NotNullImpl());
				t.String("xx",new LimitImpl(20),new CommentImpl("dd"));
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
				t.integer("id");
				t.String("xm",new LimitImpl(10),new NotNullImpl(),new CommentImpl("dd"));
				t.blob("pic");
				t.integer("age",new NotNullImpl());
				t.String("xx",new LimitImpl(20),new CommentImpl("dd"));
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
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("test"), new StringImpl(2));
		Mockito.verify(jdbcService).execute("alter table test add test varchar2(2);");
	}

	@Test
	public void testAddColumn2() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("sex"), new StringImpl(2),new CommentImpl("性别"));
		Mockito.verify(jdbcService).execute("alter table test add sex varchar2(2);\ncomment on column test.sex is '性别';");
	}
	
	@Test
	public void testAddColumn3() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("default"), new StringImpl(8),new CommentImpl("性别"),new DefaultImpl("默认值"));
		Mockito.verify(jdbcService).execute("alter table test add default varchar2(8) default '默认值';\ncomment on column test.default is '性别';");
	}
	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefault() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("aa"), new StringImpl(2), new CommentImpl("test"));
		Mockito.verify(jdbcService).execute("alter table test add aa varchar2(2);\ncomment on column test.aa is 'test';");
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeNullOrNotNull() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("bb"), new StringImpl(4),  new DefaultImpl("test"));
		Mockito.verify(jdbcService).execute("alter table test add bb varchar2(4) default 'test';");
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefaultNullOrNotNull() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("cc"), new StringImpl(2), new NotNullImpl());
		Mockito.verify(jdbcService).execute("alter table test add cc varchar2(2) not null;");
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeComment() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("dd"), new StringImpl(4),  new DefaultImpl("test"), new CommentImpl("test"));
		Mockito.verify(jdbcService).execute("alter table test add dd varchar2(4) default 'test';\ncomment on column test.dd is 'test';");
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefaultComment() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("ee"), new StringImpl(4),new DefaultImpl("test"), new NotNullImpl());
		Mockito.verify(jdbcService).execute("alter table test add ee varchar2(4) default 'test' not null;");
	}

	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#addColumn(lichen.migration.core.define.name.TableName, lichen.migration.core.define.name.ColumnName, lichen.migration.core.define.ColumnType, lichen.migration.core.define.column.NullOrNotNull, lichen.migration.core.define.column.Comment)}.
	 */
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeNullOrNotNullComment() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("ff"), new StringImpl(4),  new NotNullImpl(), new CommentImpl("test"));
		Mockito.verify(jdbcService).execute("alter table test add ff varchar2(4) not null;\ncomment on column test.ff is 'test';");
	}

	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#addColumn(lichen.migration.core.define.name.TableName, lichen.migration.core.define.name.ColumnName, lichen.migration.core.define.ColumnType, lichen.migration.core.define.column.Default, lichen.migration.core.define.column.NullOrNotNull, lichen.migration.core.define.column.Comment)}.
	 */
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefaultNullOrNotNullComment() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("gg"), new StringImpl(4), new DefaultImpl("test"), new NotNullImpl(), new CommentImpl("test"));
		Mockito.verify(jdbcService).execute("alter table test add gg varchar2(4) default 'test' not null;\ncomment on column test.gg is 'test';");
	}

	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#addComment(lichen.migration.core.define.name.TableName, lichen.migration.core.define.name.ColumnName, lichen.migration.core.define.column.Comment)}.
	 */
	@Test
	public void testAddComment() throws SQLException{
		service.setJdbcService(jdbcService);
		service.changeColumn(new TableNameImpl("test"), new ColumnNameImpl("aa"), new CommentImpl("test"));
		Mockito.verify(jdbcService).execute("comment on column test.aa is 'test';");
	}

	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#updateComment(lichen.migration.core.define.name.TableName, lichen.migration.core.define.name.ColumnName, lichen.migration.core.define.column.Comment)}.
	 */
	@Test
	public void testUpdateComment() throws SQLException{
		service.setJdbcService(jdbcService);
		service.changeColumn(new TableNameImpl("test"), new ColumnNameImpl("bb"), new CommentImpl("aa"));
		Mockito.verify(jdbcService).execute("comment on column test.bb is 'aa';");
	}

	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#addDefault(lichen.migration.core.define.name.TableName, lichen.migration.core.define.name.ColumnName, lichen.migration.core.define.column.Default)}.
	 */
	@Test
	public void testAddDefault() throws SQLException{
		service.setJdbcService(jdbcService);
		service.changeColumn(new TableNameImpl("test"), new ColumnNameImpl("cc"), new DefaultImpl("test"));
		Mockito.verify(jdbcService).execute("alter table test modify cc default 'test';");
	}

	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#updateDefault(lichen.migration.core.define.name.TableName, lichen.migration.core.define.name.ColumnName, lichen.migration.core.define.column.Default)}.
	 */
	@Test
	public void testUpdateDefault() throws SQLException{
		service.setJdbcService(jdbcService);
		service.changeColumn(new TableNameImpl("test"), new ColumnNameImpl("xx"), new DefaultImpl("dddd"));
		Mockito.verify(jdbcService).execute("alter table test modify xx default 'dddd';");
	}

	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#addIndex(lichen.migration.core.define.name.TableName, lichen.migration.core.define.name.IndexName, lichen.migration.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddIndexTableNameIndexNameColumnNameArray()throws SQLException {
		service.setJdbcService(jdbcService);
		service.addIndex(new TableNameImpl("zdry.test"), new IndexNameImpl("zdry.index"), new ColumnNameImpl("aa"),new ColumnNameImpl("dd"));
		Mockito.verify(jdbcService).execute("create index zdry.index on zdry.test (aa,dd);");
	}

	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#addIndex(lichen.migration.core.define.name.TableName, lichen.migration.core.define.name.IndexName, lichen.migration.core.define.IndexType, lichen.migration.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddIndexTableNameIndexNameIndexTypeColumnNameArray() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addIndex(new TableNameImpl("test"), new IndexNameImpl("index2"), new ColumnNameImpl("bb"));
		Mockito.verify(jdbcService).execute("create unique index index2 on test (bb);");
	}

	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#addPrimaryKey(java.lang.String, lichen.migration.core.define.name.TableName, lichen.migration.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddPrimaryKey() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addPrimaryKey(new PrimaryKeyNameImpl("pk"), new TableNameImpl("zdry.test"), new ColumnNameImpl("id"));
		Mockito.verify(jdbcService).execute("alter table zdry.test add constraint pk primary key (id)");
	}


	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#addUnique(java.lang.String, lichen.migration.core.define.name.TableName, lichen.migration.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddUnique() throws SQLException{
		service.setJdbcService(jdbcService);
		service.addUnique(new UniqueNameImpl("u1"), new TableNameImpl("test"), new ColumnNameImpl("xx"));
		Mockito.verify(jdbcService).execute("alter table test add constraint u1 unique (xx);");
	}

	

	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#dropIndex(java.lang.String)}.
	 */
	@Test
	public void testDropIndex() throws SQLException{
		service.setJdbcService(jdbcService);
		service.dropIndex(new IndexNameImpl("index2"));
		Mockito.verify(jdbcService).execute("drop index index2;");
	}

	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#dropColumn(lichen.migration.core.define.name.TableName, lichen.migration.core.define.name.ColumnName)}.
	 */
	@Test
	public void testDropColumn() throws SQLException{
		service.setJdbcService(jdbcService);
		service.dropColumn(new TableNameImpl("test"), new ColumnNameImpl("age"));
		Mockito.verify(jdbcService).execute("alter table test drop column age;");
	}

	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#dropPrimaryKey(lichen.migration.core.define.name.TableName, java.lang.String)}.
	 */
	@Test
	public void testDropPrimaryKey() throws SQLException{
		service.setJdbcService(jdbcService);
		service.dropPrimaryKey(new TableNameImpl("zdry.test"));
		Mockito.verify(jdbcService).execute("alter table zdry.test drop constraint PK;");
	}

	
	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#dropUnique(lichen.migration.core.define.name.TableName, java.lang.String)}.
	 */
	@Test
	public void testDropUnique() throws SQLException{
		service.setJdbcService(jdbcService);
		service.dropUnique(new TableNameImpl("test"), new UniqueNameImpl("u1"));
		Mockito.verify(jdbcService).execute("alter table test drop constraint u1;");
	}
	
	/**
	 * Test method for {@link lichen.migration.services.DatabaseService#dropTable(java.lang.String)}.
	 */
	@Test
	public void testDropTable() throws SQLException{
		service.setJdbcService(jdbcService);
		service.dropTable(new TableNameImpl("test"));
		Mockito.verify(jdbcService).execute("drop table test;");
	}
}
