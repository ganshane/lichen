/*		
 * Copyright 2013-5-2 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,AbstractMigration.java,fangj Exp$
 * created at:上午09:55:57
 */
package com.egf.db.migration;

import java.sql.Connection;

import com.egf.db.core.define.Comment;
import com.egf.db.core.define.column.Limit;
import com.egf.db.core.define.column.NotNull;
import com.egf.db.core.define.column.Null;
import com.egf.db.core.define.column.type.Blob;
import com.egf.db.core.define.column.type.Clob;
import com.egf.db.core.define.column.type.Number;
import com.egf.db.core.define.column.type.Varchar2;
import com.egf.db.core.define.index.type.Bitmap;
import com.egf.db.core.define.index.type.Normal;
import com.egf.db.core.define.key.ForeignKey;
import com.egf.db.core.define.key.PrimaryKey;
import com.egf.db.core.define.key.Unique;
import com.egf.db.core.define.name.ColumnName;
import com.egf.db.core.define.name.IndexName;
import com.egf.db.core.define.name.TableName;
import com.egf.db.core.jdbc.DBConnectionManager;
import com.egf.db.services.DatabaseService;
import com.egf.db.services.impl.DatabaseServiceImpl;

/**
 * 升级服务抽象类
 * 
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public abstract class AbstractMigration implements Migration {

	protected final static Unique UNIQUE = new Unique();
	
	protected final static com.egf.db.core.define.index.type.Unique UNIQUE_INDEX = new com.egf.db.core.define.index.type.Unique();
	
	protected final static Bitmap BITMAP = new Bitmap();
	
	protected final static Normal NORMAL = new Normal();

	protected final static NotNull NOTNULL = new NotNull();

	protected final static Null NULL = new Null();

	protected final static Blob blob = new Blob();

	protected final static Clob CLOB_DEFINE = new Clob();

	protected final static PrimaryKey PRIMARY_KYE = new PrimaryKey();

	protected final static ForeignKey FOREIGN_KEY = new ForeignKey();

	private   Connection connection=DBConnectionManager.getConnection();
	
	protected DatabaseService service = new DatabaseServiceImpl(connection);

	
	protected static TableName Table(String name) {
		return new TableName(name);
	}

	protected static ColumnName Column(String name) {
		return new ColumnName(name);
	}

	protected static Limit Limit(int length) {
		return new Limit(length);
	}
	
	protected static IndexName Index(String name) {
		return new IndexName(name);
	}

	protected static Comment Comment(String comment) {
		return new Comment(comment);
	}

	protected static Varchar2 Varchar2(int length) {
		return new Varchar2(length);
	}

	protected static Number Number(int length) {
		return new Number(length);
	}

}
