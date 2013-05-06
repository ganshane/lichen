/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DatabaseServiceImpl.java,fangj Exp$
 * created at:下午01:08:24
 */
package com.egf.db.services.impl;

import org.apache.log4j.Logger;

import com.egf.db.core.CreateTableCallback;
import com.egf.db.core.define.ColumnType;
import com.egf.db.core.define.IndexType;
import com.egf.db.core.define.column.ColumnDefine;
import com.egf.db.core.define.column.Comment;
import com.egf.db.core.define.name.ColumnName;
import com.egf.db.core.define.name.IndexName;
import com.egf.db.core.define.name.TableName;
import com.egf.db.services.DatabaseService;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
class DatabaseServiceImpl implements DatabaseService{

	Logger logger=Logger.getLogger(DatabaseServiceImpl.class);


	public void addColumn(TableName table, ColumnName column,
			ColumnType columnType, ColumnDefine... defines) {
		TableNameImpl tableImpl=(TableNameImpl)table;
		ColumnNameImpl columnImpl=(ColumnNameImpl)column;
		String tableName=tableImpl.getName();
		String columnName=columnImpl.getName();
		String type=columnType.getColumnType();
		
		StringBuffer sb=new StringBuffer("alter table ");
		sb.append(tableName);
		sb.append("add");
		sb.append(columnName);
		sb.append(type);
		
	}

	public void addForeignKey(String name, TableName table,
			ColumnName... Column) {
		// TODO Auto-generated method stub
		
	}

	public void addIndex(TableName table, IndexName Index,
			ColumnName... columnName) {
		// TODO Auto-generated method stub
		
	}

	public void addIndex(TableName table, IndexName Index, IndexType indexType,
			ColumnName... Column) {
		// TODO Auto-generated method stub
		
	}

	public void addPrimaryKey(String name, TableName table,
			ColumnName... Column) {
		// TODO Auto-generated method stub
		
	}

	public void addUnique(String name, TableName table, ColumnName... Column) {
		// TODO Auto-generated method stub
		
	}

	public void createTable(TableName table, Comment comment,
			CreateTableCallback callback) {
		// TODO Auto-generated method stub
		
	}

	public void createTable(TableName table, CreateTableCallback callback) {
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
