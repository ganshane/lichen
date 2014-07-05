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
package lichen.creeper.core.services;

import lichen.creeper.core.config.CreeperCoreConfig;
import junit.framework.Assert;
import lichen.core.services.Option;
import org.junit.Test;


/**
 * xml loader test.
 * @author jcai
 */
public class XmlLoaderTest {
    @Test
    public void testOutput() throws Throwable {
        CreeperCoreConfig config = new CreeperCoreConfig();
        CreeperCoreConfig.JpaProperty property = new CreeperCoreConfig.JpaProperty();
        property.name="haha";
        property.value="haha";
        config.jpaProperties.add(property);
        System.out.println(XmlLoader.toXml(config));
    }
    @Test
    public void testXml() throws Throwable {
        CreeperCoreConfig config  = XmlLoader.parseXML(CreeperCoreConfig.class,
                getClass().getResourceAsStream("/test_creeper_core_config.xml"),
                Option.some(getClass().getResourceAsStream("/lichen/creeper/core/config/CreeperCoreConfig.xsd")));
        Assert.assertEquals("org.h2.Driver", config.db.driverClassName);
    }
}
