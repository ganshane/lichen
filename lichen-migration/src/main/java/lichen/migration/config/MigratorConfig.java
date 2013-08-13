// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 针对migrator的配置.
 * @author jcai
 */
@XmlRootElement(name = "migrator")
public class MigratorConfig {
    /**
     * 数据库驱动类.
     */
    @XmlElement(name = "driver_class")
    public String driverClassName;
    /**
     * 数据库用户名.
     */
    @XmlElement(name = "user_name")
    public String username;
    /**
     * 数据库用户密码.
     */
    @XmlElement(name = "password")
    public String password;
    /**
     * 数据库url地址.
     */
    @XmlElement(name = "url")
    public String url;
    /**
     * java类包名.
     */
    @XmlElement(name = "package")
    public String migratePackage;
}
