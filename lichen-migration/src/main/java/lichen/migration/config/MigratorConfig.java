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
	public String getDriverClassName() {
		return driverClassName;
	}
	/**
	 * @param driverClassName the driverClassName to set
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
    /**
     * @return the migratePackage
     */
    public String getMigratePackage() {
    	return migratePackage;
	}
    /**
	 * @param migratePackage the migratePackage to set
	 */
	public void setMigratePackage(String migratePackage) {
		this.migratePackage = migratePackage;
	}
}
