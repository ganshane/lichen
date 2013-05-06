/*		
 * Copyright 2013-4-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DatabaseService.java,fangj Exp$
 * created at:下午12:42:36
 */
package com.egf.db.services;

import com.egf.db.core.CreateTableCallback;
import com.egf.db.core.define.ColumnType;
import com.egf.db.core.define.IndexType;
import com.egf.db.core.define.column.ColumnDefine;
import com.egf.db.core.define.column.Comment;
import com.egf.db.core.define.name.ColumnName;
import com.egf.db.core.define.name.IndexName;
import com.egf.db.core.define.name.TableName;

/**
 * 数据库服务接口
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public interface DatabaseService {
	
	public void createTable(TableName table,Comment comment, CreateTableCallback callback);
	
	public void createTable(TableName table, CreateTableCallback callback);
	
	public void addColumn(TableName table,ColumnName column,ColumnType columnType,ColumnDefine ... defines);
	
	public void addIndex(TableName table,IndexName index, ColumnName ... columnName);
	
	public void addIndex(TableName table,IndexName index,IndexType indexType, ColumnName ...Column);
	
	public void addPrimaryKey(String name,TableName table,ColumnName ... Column);
	
	public void addForeignKey(String name,TableName table,ColumnName ... Column);
	
	public void addUnique(String name,TableName table,ColumnName ... Column);
	
	public void dropTable(String name);
	
	public void dropIndex(String name);
	
	public void dropColumn(String name);
	
	public void dropPrimaryKey(String name);
	
	public void dropForeignKey(String name);
	
	public void dropUnique(String name);

	
}
