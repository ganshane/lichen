// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.model;

/**
 * 列的默认值
 * @author jcai
 */
public interface Default extends ColumnOption{
    /**
     * 返回列的默认值
     * @return 列的默认值
     */
    public String getValue();
}
