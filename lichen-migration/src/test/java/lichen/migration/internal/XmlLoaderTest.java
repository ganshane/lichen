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
package lichen.migration.internal;

import junit.framework.Assert;
import lichen.migration.config.MigratorConfig;
import org.junit.Test;


/**
 * xml loader test.
 * @author jcai
 */
public class XmlLoaderTest {
    @Test
    public void testXml() throws Throwable {
        MigratorConfig config = XmlLoader.parseXML(MigratorConfig.class,
                getClass().getResourceAsStream("/test_config.xml"),
                Option.some(getClass().getResourceAsStream("/migrator-config.xsd")));
        Assert.assertEquals("org.h2.Driver", config._driverClassName);
        Assert.assertEquals("lichen.migration.testdb", config._migratePackage);
    }
}
