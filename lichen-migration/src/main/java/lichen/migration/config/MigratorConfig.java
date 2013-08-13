// Copyright 2013 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
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
