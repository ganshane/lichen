package lichen.creeper.core.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * lichen.creeper core config
 * @author jcai
 */
@XmlRootElement(name="creeper_core")
public class CreeperCoreConfig {
	
	//日志路径
	@XmlElement(name="log_file")
	public String log_file;
	
    public static class Database{
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
    }

    /**
     * JPA属性配置
     */
    @XmlRootElement(name="property")
    public static class JpaProperty{
    		//属性名称
        @XmlAttribute(name="name")
        public String name;
        //属性的值
        @XmlAttribute(name="value")
        public String value;
    }
    /** 数据库相关配置 **/
    @XmlElement(name="db")
    public Database db = new Database();
    /** JPA相关配置 **/
    @XmlElementWrapper(name="jpa")
    @XmlElement(name="property")
    public List<JpaProperty> jpaProperties = new ArrayList<JpaProperty>();

}
