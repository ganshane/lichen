// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.model;

/**
 * 对名称的定义
 * @author jcai
 */
public interface Name extends ForeignKeyOption,IndexOption{
    public String getValue();
}
