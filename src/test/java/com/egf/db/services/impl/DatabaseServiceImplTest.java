/*		
 * Copyright 2013-5-6 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DatabaseServiceImplTest.java,fangj Exp$
 * created at:下午03:17:42
 */
package com.egf.db.services.impl;

import java.sql.SQLException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.egf.db.core.CreateTableCallback;
import com.egf.db.core.jdbc.JdbcService;
import com.egf.db.core.model.Table;
import com.egf.db.services.DatabaseService;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class DatabaseServiceImplTest {
	
	DatabaseServiceImpl service=new DatabaseServiceImpl();

	/**
	 * Test method for {@link com.egf.db.services.impl.DatabaseServiceImpl#addColumn(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.ColumnType, com.egf.db.core.define.column.ColumnDefine[])}.
	 */
	@Test
	public void testCreateTable() throws SQLException{
		service.createTable(new TableNameImpl("test"),new CommentImpl("表注释"), new CreateTableCallback() {
			public void doCreateAction(Table t) {
				t.number("id");
				t.varchar2("xm",new LimitImpl(10),new NotNullImpl(),new CommentImpl("dd"));
				t.blob("pic");
				t.number("age",new NotNullImpl());
				t.varchar2("xx",new LimitImpl(20),new CommentImpl("dd"));
			}
		});
	}
	
	@Test
	public void testCreateTable2() throws SQLException{
		service.createTable(new TableNameImpl("test2"), new CreateTableCallback() {
			public void doCreateAction(Table t) {
				t.number("id");
				t.varchar2("xm",new LimitImpl(10),new NotNullImpl(),new CommentImpl("dd"));
				t.blob("pic");
				t.number("age",new NotNullImpl());
				t.varchar2("xx",new LimitImpl(20),new CommentImpl("dd"));
			}
		});
	}
	
	@Test
	public void testAddColumn() throws SQLException{
		/*
		service.setJdbcService(new JdbcService(){

			public void execute(String sql) throws SQLException {
				Assert.assertEquals("xxx", sql);
				
			}

			public void execute(String sql, Object[] params)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}

			public List<Object[]> find(String sql) {
				// TODO Auto-generated method stub
				return null;
			}

			public List<Object[]> find(String sql, Object[] params) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getColumnTypeName(String tableName, String columnName) {
				// TODO Auto-generated method stub
				return null;
			}

			public String[] getTablePK(String tableName) {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		*/
		JdbcService jdbcService = Mockito.mock(JdbcService.class);
		service.setJdbcService(jdbcService);
		
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("test"), new Varchar2Impl(2));
		
		
		Mockito.verify(jdbcService).execute("alter table test add test varchar2(2);");
	}

	@Test
	public void testAddColumn2() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("sex"), new Varchar2Impl(2),new CommentImpl("性别"));
	}
	
	@Test
	public void testAddColumn3() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("default"), new Varchar2Impl(2),new CommentImpl("性别"),new DefaultImpl("默认值"));
	}
	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefault() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("aa"), new Varchar2Impl(2), new CommentImpl("test"));
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeNullOrNotNull() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("bb"), new Varchar2Impl(2),  new DefaultImpl("test"));
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefaultNullOrNotNull() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("cc"), new Varchar2Impl(2), new NotNullImpl());
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeComment() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("dd"), new Varchar2Impl(2),  new DefaultImpl("test"), new CommentImpl("test"));
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefaultComment() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("ee"), new Varchar2Impl(2),new DefaultImpl("test"), new NotNullImpl());
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addColumn(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.ColumnType, com.egf.db.core.define.column.NullOrNotNull, com.egf.db.core.define.column.Comment)}.
	 */
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeNullOrNotNullComment() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("ff"), new Varchar2Impl(2),  new NotNullImpl(), new CommentImpl("test"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addColumn(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.ColumnType, com.egf.db.core.define.column.Default, com.egf.db.core.define.column.NullOrNotNull, com.egf.db.core.define.column.Comment)}.
	 */
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefaultNullOrNotNullComment() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("gg"), new Varchar2Impl(2), new DefaultImpl("test"), new NotNullImpl(), new CommentImpl("test"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addComment(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.column.Comment)}.
	 */
	@Test
	public void testAddComment() throws SQLException{
		service.addComment(new TableNameImpl("test"), new ColumnNameImpl("aa"), new CommentImpl("test"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#updateComment(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.column.Comment)}.
	 */
	@Test
	public void testUpdateComment() throws SQLException{
		service.addComment(new TableNameImpl("test"), new ColumnNameImpl("bb"), new CommentImpl("aa"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addDefault(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.column.Default)}.
	 */
	@Test
	public void testAddDefault() throws SQLException{
		service.addDefault(new TableNameImpl("test"), new ColumnNameImpl("cc"), new DefaultImpl("test"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#updateDefault(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.column.Default)}.
	 */
	@Test
	public void testUpdateDefault() throws SQLException{
		service.addDefault(new TableNameImpl("test"), new ColumnNameImpl("dd"), new DefaultImpl("dddd"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addIndex(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.IndexName, com.egf.db.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddIndexTableNameIndexNameColumnNameArray()throws SQLException {
		service.addIndex(new TableNameImpl("test"), new IndexNameImpl("index"), new ColumnNameImpl("aa"),new ColumnNameImpl("dd"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addIndex(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.IndexName, com.egf.db.core.define.IndexType, com.egf.db.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddIndexTableNameIndexNameIndexTypeColumnNameArray() throws SQLException{
		service.addIndex(new TableNameImpl("test"), new IndexNameImpl("index2"), new IndexUniqueImpl(), new ColumnNameImpl("bb"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addPrimaryKey(java.lang.String, com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddPrimaryKey() throws SQLException{
		service.addPrimaryKey("pk", new TableNameImpl("test"), new ColumnNameImpl("id"));
	}


	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addUnique(java.lang.String, com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddUnique() throws SQLException{
		service.addUnique("u1", new TableNameImpl("test"), new ColumnNameImpl("xx"));
	}

	

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropIndex(java.lang.String)}.
	 */
	@Test
	public void testDropIndex() throws SQLException{
		service.dropIndex("index2");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropColumn(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName)}.
	 */
	@Test
	public void testDropColumn() throws SQLException{
		service.dropColumn(new TableNameImpl("test"), new ColumnNameImpl("age"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropPrimaryKey(com.egf.db.core.define.name.TableName, java.lang.String)}.
	 */
	@Test
	public void testDropPrimaryKey() throws SQLException{
		service.dropPrimaryKey(new TableNameImpl("test"), "pk");
	}

	
	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropUnique(com.egf.db.core.define.name.TableName, java.lang.String)}.
	 */
	@Test
	public void testDropUnique() throws SQLException{
		service.dropUnique(new TableNameImpl("test"), "u1");
	}
	
	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropTable(java.lang.String)}.
	 */
	@Test
	public void testDropTable() throws SQLException{
		service.dropTable("test");
	}
}
