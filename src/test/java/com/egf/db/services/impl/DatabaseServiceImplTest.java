/*		
 * Copyright 2013-5-6 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DatabaseServiceImplTest.java,fangj Exp$
 * created at:下午03:17:42
 */
package com.egf.db.services.impl;

import static org.junit.Assert.*;

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
	public void testAddColumn() {
		service.createTable(new TableNameImpl("test"),new CommentImpl("表注释"), new CreateTableCallback() {
			public void doCreateAction(Table t) {
				t.varchar2("xm",new LimitImpl(10),new NotNullImpl(),new CommentImpl("dd"));
				t.blob("pic");
				t.number("xm",new NotNullImpl());
				t.varchar2("xx",new LimitImpl(20),new CommentImpl("dd"));
			}
		});
	}

}
