package creeper.core.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * creeper core config
 * @author jcai
 */
@XmlRootElement(name="creeper_core")
public class CreeperCoreConfig {
    public static class Database{
        /**
         * 数据库驱动类.
         */
        @XmlElement(name = "driver_class")
        public String _driverClassName;
        /**
         * 数据库用户名.
         */
        @XmlElement(name = "user_name")
        public String _username;
        /**
         * 数据库用户密码.
         */
        @XmlElement(name = "password")
        public String _password;
        /**
         * 数据库url地址.
         */
        @XmlElement(name = "url")
        public String _url;
    }
    //数据库实例
    @XmlElement(name="db")
    public Database db = new Database();
}
