/*		
 * Copyright 2013-5-2 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,AbstractMigration.java,fangj Exp$
 * created at:上午09:55:57
 */
package lichen.migration.services.impl;

import lichen.migration.core.define.column.Comment;
import lichen.migration.core.define.column.Limit;
import lichen.migration.core.define.column.NotNull;
import lichen.migration.core.define.column.PrimaryKey;
import lichen.migration.core.define.column.Unique;
import lichen.migration.core.define.column.types.Blob;
import lichen.migration.core.define.column.types.Clob;
import lichen.migration.core.define.column.types.Date;
import lichen.migration.core.define.name.ColumnName;
import lichen.migration.core.define.name.ForeignKeyName;
import lichen.migration.core.define.name.IndexName;
import lichen.migration.core.define.name.PrimaryKeyName;
import lichen.migration.core.define.name.SequenceName;
import lichen.migration.core.define.name.TableName;
import lichen.migration.core.define.name.UniqueName;
import lichen.migration.services.DatabaseService;
import lichen.migration.services.Migration;

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
	/**integer数字**/
	protected final static lichen.migration.core.define.column.types.Integer INTEGER=new IntegerImpl();
	/**时间**/
	protected final static Date DATE=new DateImpl();
	/**blob**/
	protected final static Blob BLOB=new BlobImpl();
	/**clob**/
	protected final static Clob CLOB=new ClobImpl();
	/**主键**/
	protected final static PrimaryKey IS_PRIMARYKEY=new PrimaryKeyImpl();
	
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
	 * 创建序列
	 * @param name 序列名称
	 * @return
	 */
	protected static SequenceName SequenceName(String name){
		return new SequenceNameImpl(name);
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
	 * 主键名称
	 * @param name 名称
	 * @return
	 */
	protected static PrimaryKeyName PrimaryKeyName(String name) {
		return new PrimaryKeyNameImpl(name);
	}
	
	/**
	 * 外键名称
	 * @param name 名称
	 * @return
	 */
	protected static ForeignKeyName ForeignKeyName(String name) {
		return new ForeignKeyNameImpl(name);
	}
	
	/**
	 * 唯一名称
	 * @param name 名称
	 * @return
	 */
	protected static UniqueName UniqueName(String name) {
		return new UniqueNameImpl(name);
	}
	
	/**
	 * 创建varchar2 类型
	 * @param length 长度
	 * @return
	 */
	protected static StringImpl String(int length) {
		return new StringImpl(length);
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
	protected static lichen.migration.core.define.column.Default Default(String value) {
		return new DefaultImpl(value);
	}

}
