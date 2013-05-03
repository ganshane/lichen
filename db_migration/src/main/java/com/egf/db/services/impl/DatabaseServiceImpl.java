/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DatabaseServiceImpl.java,fangj Exp$
 * created at:下午01:08:24
 */
package com.egf.db.services.impl;

import java.sql.Connection;

import com.egf.db.core.CreateTableCallback;
import com.egf.db.core.define.ColumnType;
import com.egf.db.core.define.Comment;
import com.egf.db.core.define.IndexType;
import com.egf.db.core.define.column.ColumnDefine;
import com.egf.db.core.define.name.ColumnName;
import com.egf.db.core.define.name.IndexName;
import com.egf.db.core.define.name.TableName;
import com.egf.db.core.jdbc.JdbcService;
import com.egf.db.core.jdbc.JdbcServiceImpl;
import com.egf.db.services.DatabaseService;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class DatabaseServiceImpl implements DatabaseService{

	
	private JdbcService jdbcService;
	
	private Connection connection;
	
	public DatabaseServiceImpl(Connection connection){
		jdbcService=new JdbcServiceImpl();
		this.connection=connection;
	}

	
	public void addColumn(TableName Table, ColumnName Column,
			ColumnType columnType, Comment Comment, ColumnDefine... defines) {
		// TODO Auto-generated method stub
		
	}

	
	public void addColumn(TableName Table, ColumnName Column,
			ColumnType columnType, ColumnDefine... defines) {
		// TODO Auto-generated method stub
		
	}

	
	public void addForeignKey(String name, TableName Table,
			ColumnName... Column) {
		// TODO Auto-generated method stub
		
	}

	
	public void addIndex(TableName Table, IndexName Index,
			ColumnName... columnName) {
		// TODO Auto-generated method stub
		
	}

	
	public void addIndex(TableName Table, IndexName Index, IndexType indexType,
			ColumnName... Column) {
		// TODO Auto-generated method stub
		
	}

	
	public void addPrimaryKey(String name, TableName Table,
			ColumnName... Column) {
		// TODO Auto-generated method stub
		
	}

	
	public void addUnique(String name, TableName Table, ColumnName... Column) {
		// TODO Auto-generated method stub
		
	}

	
	public void createTable(TableName Table, Comment Comment,
			CreateTableCallback callback) {
		// TODO Auto-generated method stub
		
	}

	
	public void createTable(TableName Table, CreateTableCallback callback) {
		// TODO Auto-generated method stub
		
	}

	
	public void dropColumn(String name) {
		// TODO Auto-generated method stub
		
	}

	
	public void dropForeignKey(String name) {
		// TODO Auto-generated method stub
		
	}

	
	public void dropIndex(String name) {
		// TODO Auto-generated method stub
		
	}


	public void dropPrimaryKey(String name) {
		// TODO Auto-generated method stub
		
	}

	
	public void dropTable(String name) {
		// TODO Auto-generated method stub
		
	}

	
	public void dropUnique(String name) {
		// TODO Auto-generated method stub
		
	}


}
