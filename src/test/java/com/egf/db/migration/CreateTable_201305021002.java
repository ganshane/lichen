/*		
 * Copyright 2013-5-2 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,CreateTable_201305021002.java,fangj Exp$
 * created at:上午10:02:30
 */
package com.egf.db.migration;

import org.junit.Test;

import com.egf.db.core.CreateTableCallback;
import com.egf.db.core.model.Table;
import com.egf.db.services.impl.AbstractMigration;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class CreateTable_201305021002 extends AbstractMigration {

	@Test
	public void up() {
		//创建表
		createTable(TableName("test"), Comment("test注释"), new CreateTableCallback() {
			public void doCreateAction(Table t) {
				t.varchar2("xm", Limit(10),NOTNULL,Comment("dd"));
				t.blob("pic");
				t.number("xm",NOTNULL);
				t.varchar2("xx",Limit(20),Comment("dd"));
			}
		});
		//增加列
		addColumn(TableName("table"), ColumnName("c"),Varchar2(2),NOTNULL,Comment("test"));
		addColumn(TableName("ss"), ColumnName(""), Number(0), Comment("test"));
		addIndex(TableName("test"), IndexName("test"), ColumnName("name"),ColumnName("dd"));
		//创建索引
	
	}

	public void down() {
		
	}

	
	
}
