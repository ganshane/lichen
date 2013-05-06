/*		
 * Copyright 2013-5-2 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,AbstractMigration.java,fangj Exp$
 * created at:上午09:55:57
 */
package com.egf.db.services.impl;

import com.egf.db.core.define.column.Comment;
import com.egf.db.core.define.column.Limit;
import com.egf.db.core.define.column.types.Varchar2;
import com.egf.db.core.define.name.ColumnName;
import com.egf.db.core.define.name.TableName;
import com.egf.db.services.DatabaseService;
import com.egf.db.services.Migration;

/**
 * 升级服务抽象类
 * 
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public abstract class AbstractMigration extends DatabaseServiceImpl implements Migration,DatabaseService{
	

	protected static TableName Table(String name) {
		return new TableNameImpl(name);
	}
	
	
	protected static ColumnName Column(String name) {
		return new ColumnNameImpl(name);
	}

	protected static Limit Limit(int length) {
		return new LimitImpl(length);
	}
	
	protected static Comment Comment(String comment) {
		return new CommentImpl(comment);
	}

	protected static Varchar2 Varchar2(int length) {
		return new Varchar2Impl(length);
	}


}
