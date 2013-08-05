// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.testdb.index;

import lichen.migration.internal.OptionsImpl;
import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;

import javax.inject.Inject;

/** 
 * @Description: 在单个字段上创建索引的测试用例脚本
 *
 * @author zhaoyong
 * 
 * @date 2013-8-5 上午10:56:32
 *
 * @version 1.0
 */
public class Migrate_20130805105544_CreateSingleColumnIndex implements Migration{
    @Inject
    private MigrationHelper helper;
    @Inject
    private Options options;

    @Override
    public void up() throws Throwable {
    	//创建唯一索引
        helper.addIndex("test_table", "field4", options.Unique());
    }

    @Override
    public void down() throws Throwable {
    	//删除未指定名称的索引（单列）
    	helper.removeIndex("test_table", "field4");
    }
}
