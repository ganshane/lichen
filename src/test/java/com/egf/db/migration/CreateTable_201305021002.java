/*		
 * Copyright 2013-5-2 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,CreateTable_201305021002.java,fangj Exp$
 * created at:上午10:02:30
 */
package com.egf.db.migration;

import com.egf.db.core.CreateTableCallback;
import com.egf.db.core.model.TableModel;

/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class CreateTable_201305021002 extends AbstractMigration{

	public void up() {
		//创建表
		service.createTable(Table("dd"), new CreateTableCallback() {
			public void doCreateAction(TableModel t) {
				t.varchar2("", Limit(10), Comment(""), NOTNULL);
				t.blob("", NOTNULL);
			}
		});
		//增加列	
		service.addColumn(Table(""), Column("dd"),Varchar2(2),Comment(""), UNIQUE,NOTNULL);
		//增加索引
		service.addIndex(Table(""),Index("dd"),Column(""),Column(""));
		//增加有类型的索引
		service.addIndex(Table(""), Index(""), NORMAL,Column(""));
		//增加外键
		service.addForeignKey("", Table(""), Column(""),Column(""));
	
	}

	public void down() {
		
	}

	
	
}
