/*		
 * Copyright 2013-5-6 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DatabaseServiceImplTest.java,fangj Exp$
 * created at:下午03:17:42
 */
package com.egf.db.services.impl;

import java.sql.SQLException;

import org.junit.Test;

import com.egf.db.core.CreateTableCallback;
import com.egf.db.core.model.Table;
import com.egf.db.services.DatabaseService;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class DatabaseServiceImplTest {
	
	DatabaseService service=new DatabaseServiceImpl();

	/**
	 * Test method for {@link com.egf.db.services.impl.DatabaseServiceImpl#addColumn(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.ColumnType, com.egf.db.core.define.column.ColumnDefine[])}.
	 */
	@Test
	public void testCreateTable() throws SQLException{
		service.createTable(new TableNameImpl("test"),new CommentImpl("表注释"), new CreateTableCallback() {
			public void doCreateAction(Table t) {
				t.varchar2("xm",new LimitImpl(10),new NotNullImpl(),new CommentImpl("dd"));
				t.blob("pic");
				t.number("xm",new NotNullImpl());
				t.varchar2("xx",new LimitImpl(20),new CommentImpl("dd"));
			}
		});
	}
	
	@Test
	public void testAddColumn() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("test"), new Varchar2Impl(2));
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefault() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("test"), new Varchar2Impl(2), new CommentImpl("test"));
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeNullOrNotNull() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("test"), new Varchar2Impl(2),  new DefaultImpl("test"));
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefaultNullOrNotNull() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("test"), new Varchar2Impl(2), new NotNullImpl());
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeComment() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("test"), new Varchar2Impl(2),  new DefaultImpl("test"), new CommentImpl("test"));
	}

	
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefaultComment() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("test"), new Varchar2Impl(2),new DefaultImpl("test"), new NotNullImpl());
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addColumn(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.ColumnType, com.egf.db.core.define.column.NullOrNotNull, com.egf.db.core.define.column.Comment)}.
	 */
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeNullOrNotNullComment() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("test"), new Varchar2Impl(2),  new NotNullImpl(), new CommentImpl("test"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addColumn(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.ColumnType, com.egf.db.core.define.column.Default, com.egf.db.core.define.column.NullOrNotNull, com.egf.db.core.define.column.Comment)}.
	 */
	@Test
	public void testAddColumnTableNameColumnNameColumnTypeDefaultNullOrNotNullComment() throws SQLException{
		service.addColumn(new TableNameImpl("test"), new ColumnNameImpl("test"), new Varchar2Impl(2), new DefaultImpl("test"), new NotNullImpl(), new CommentImpl("test"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addComment(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.column.Comment)}.
	 */
	@Test
	public void testAddComment() throws SQLException{
		service.addComment(new TableNameImpl("test"), new ColumnNameImpl("test"), new CommentImpl("test"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#updateComment(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.column.Comment)}.
	 */
	@Test
	public void testUpdateComment() throws SQLException{
		service.addComment(new TableNameImpl("test"), new ColumnNameImpl("test"), new CommentImpl("aa"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addDefault(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.column.Default)}.
	 */
	@Test
	public void testAddDefault() throws SQLException{
		service.addDefault(new TableNameImpl("test"), new ColumnNameImpl("test"), new DefaultImpl("test"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#updateDefault(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName, com.egf.db.core.define.column.Default)}.
	 */
	@Test
	public void testUpdateDefault() throws SQLException{
		service.addDefault(new TableNameImpl("test"), new ColumnNameImpl("test"), new DefaultImpl("dddd"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addIndex(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.IndexName, com.egf.db.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddIndexTableNameIndexNameColumnNameArray()throws SQLException {
		service.addIndex(new TableNameImpl("test"), new IndexNameImpl("index"), new ColumnNameImpl("test"),new ColumnNameImpl("dd"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addIndex(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.IndexName, com.egf.db.core.define.IndexType, com.egf.db.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddIndexTableNameIndexNameIndexTypeColumnNameArray() throws SQLException{
		service.addIndex(new TableNameImpl("test"), new IndexNameImpl("index"), new IndexUniqueImpl(), new ColumnNameImpl("cl1"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addPrimaryKey(java.lang.String, com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddPrimaryKey() throws SQLException{
		service.addPrimaryKey("aa", new TableNameImpl("dd"), new ColumnNameImpl("dd"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addForeignKey(java.lang.String, com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddForeignKey() throws SQLException{
		service.addForeignKey("aa", new TableNameImpl("dd"), new ColumnNameImpl("dd"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#addUnique(java.lang.String, com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName[])}.
	 */
	@Test
	public void testAddUnique() throws SQLException{
		service.addUnique("aa", new TableNameImpl("dd"), new ColumnNameImpl("dd"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropTable(java.lang.String)}.
	 */
	@Test
	public void testDropTable() throws SQLException{
		service.dropTable("test");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropIndex(java.lang.String)}.
	 */
	@Test
	public void testDropIndex() throws SQLException{
		service.dropIndex("test");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropColumn(com.egf.db.core.define.name.TableName, com.egf.db.core.define.name.ColumnName)}.
	 */
	@Test
	public void testDropColumn() throws SQLException{
		service.dropColumn(new TableNameImpl("dd"), new ColumnNameImpl("ddd"));
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropPrimaryKey(com.egf.db.core.define.name.TableName, java.lang.String)}.
	 */
	@Test
	public void testDropPrimaryKey() throws SQLException{
		service.dropPrimaryKey(new TableNameImpl("dd"), "dd");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropForeignKey(com.egf.db.core.define.name.TableName, java.lang.String)}.
	 */
	@Test
	public void testDropForeignKey() throws SQLException{
		service.dropForeignKey(new TableNameImpl("dd"), "dd");
	}

	/**
	 * Test method for {@link com.egf.db.services.DatabaseService#dropUnique(com.egf.db.core.define.name.TableName, java.lang.String)}.
	 */
	@Test
	public void testDropUnique() throws SQLException{
		service.dropUnique(new TableNameImpl("dd"), "dd");
	}
	
}
