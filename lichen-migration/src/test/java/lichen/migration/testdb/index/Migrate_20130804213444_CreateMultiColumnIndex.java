// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.testdb.index;

import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.Options;

import javax.inject.Inject;

/** 
 * @Description: 在多个字段上创建索引的测试用例脚本
 *
 * @author zhaoyong
 * 
 * @date 2013-8-4 下午21:35:32
 *
 * @version 1.0
 */
public class Migrate_20130804213444_CreateMultiColumnIndex implements Migration{
    @Inject
    private MigrationHelper helper;
    @Inject
    private Options options;

    @Override
    public void up() throws Throwable {
        helper.addIndex("test_table", new String[]{
        		"field_c",
        		"field_a",
        		"field_b"
        		});
        
    }

    @Override
    public void down() throws Throwable {
    	
    }
}
