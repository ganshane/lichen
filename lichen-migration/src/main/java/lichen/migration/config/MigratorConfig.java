package lichen.migration.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 针对migrator的配置
 * @author jcai
 */
@XmlRootElement(name = "migrator")
public class MigratorConfig {
    @XmlElement(name = "driver_class")
    public String driverClassName;
    @XmlElement(name = "user_name")
    public String username;
    @XmlElement(name = "password")
    public String password;
    @XmlElement(name = "url")
    public String url;
    @XmlElement(name = "package")
    public String migratePackage;
}
