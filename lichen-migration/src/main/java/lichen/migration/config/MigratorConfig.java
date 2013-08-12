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
    /**
     * @return the driverClassName
     */
    public final String getDriverClassName() {
        return driverClassName;
    }
    /**
     * @param newDriverClassName the driverClassName to set
     */
    public final void setDriverClassName(final String newDriverClassName) {
        this.driverClassName = newDriverClassName;
    }
    /**
     * @return the username
     */
    public final String getUsername() {
        return username;
    }

    /**
     * @param newUsername
     *            the username to set
     */
    public final void setUsername(final String newUsername) {
        this.username = newUsername;
    }
    /**
     * @return the password
     */
    public final String getPassword() {
        return password;
    }
    /**
     * @param newpassword the password to set
     */
    public final void setPassword(final String newpassword) {
        this.password = newpassword;
    }
    /**
     * @return the url
     */
    public final String getUrl() {
        return url;
    }
    /**
     * @param newurl the url to set
     */
    public final void setUrl(final String newurl) {
        this.url = newurl;
    }
    /**
     * @return the migratePackage
     */
    public final String getMigratePackage() {
        return migratePackage;
    }
    /**
     * @param newmigratePackage the migratePackage to set
     */
    public final void setMigratePackage(final String newmigratePackage) {
        this.migratePackage = newmigratePackage;
    }
}
