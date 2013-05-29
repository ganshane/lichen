/*		
 * Copyright 2013-5-2 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,AbstractMigration.java,fangj Exp$
 * created at:上午09:55:57
 */
package com.egf.db.services.impl;

import com.egf.db.core.define.column.Comment;
import com.egf.db.core.define.column.Limit;
import com.egf.db.core.define.column.NotNull;
import com.egf.db.core.define.column.PrimaryKey;
import com.egf.db.core.define.column.Unique;
import com.egf.db.core.define.column.types.Blob;
import com.egf.db.core.define.column.types.Clob;
import com.egf.db.core.define.column.types.Date;
import com.egf.db.core.define.column.types.Varchar2;
import com.egf.db.core.define.index.types.Normal;
import com.egf.db.core.define.name.ColumnName;
import com.egf.db.core.define.name.IndexName;
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
	
	/**唯一**/
	protected final static Unique UNIQUE=new UniqueImpl();
	/**不为空**/
	protected final static NotNull NOTNULL=new NotNullImpl();
	/**number数字**/
	protected final static com.egf.db.core.define.column.types.Number NUMBER=new NumberImpl();
	/**时间**/
	protected final static Date DATE=new DateImpl();
	/**blob**/
	protected final static Blob BLOB=new BlobImpl();
	/**clob**/
	protected final static Clob CLOB=new ClobImpl();
	/**主键**/
	protected final static PrimaryKey IS_PRIMARYKEY=new PrimaryKeyImpl();
	/**索引唯一类型**/
	protected final static com.egf.db.core.define.index.types.Unique UNIQUE_INDEX=new IndexUniqueImpl();
	/**索引默认类型**/
	protected final static Normal NORMAL=new com.egf.db.services.impl.Normal();
	/**索引Bitmap类型**/
	protected final static Bitmap BITMAP=new Bitmap();
	
	/**
	 * 创建表名
	 * @param name 名称
	 * 参数说明
	 * 创建用户下的表：user.table
	 * @return
	 */
	protected static TableName TableName(String name) {
		return new TableNameImpl(name);
	}
	
	/**
	 * 创建索引
	 * @param name 名称
	 * 参数说明
	 * 创建用户下的索引：user.index
	 * @return
	 */
	protected static IndexName IndexName(String name) {
		return new IndexNameImpl(name);
	}
	
	/**
	 * 创建列
	 * @param name 名称
	 * @return
	 */
	protected static ColumnName ColumnName(String name) {
		return new ColumnNameImpl(name);
	}

	/**
	 * 限制
	 * @param length 长度
	 * @return
	 */
	protected static Limit Limit(int length) {
		return new LimitImpl(length);
	}
	
	/**
	 * 创建注释
	 * @param comment 注释内容
	 * @return
	 */
	protected static Comment Comment(String comment) {
		return new CommentImpl(comment);
	}

	/**
	 * 创建varchar2 类型
	 * @param length 长度
	 * @return
	 */
	protected static Varchar2 Varchar2(int length) {
		return new Varchar2Impl(length);
	}
	
	/**
	 * 创建时间类型
	 * @return
	 */
	protected static Date Date(){
		return new DateImpl();
	}
	
	/**
	 * 创建默认值
	 * @param value 值
	 * @return
	 */
	protected static com.egf.db.core.define.column.Default Default(String value) {
		return new DefaultImpl(value);
	}

}
