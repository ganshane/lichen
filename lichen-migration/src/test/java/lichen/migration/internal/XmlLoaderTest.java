// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.internal;

import junit.framework.Assert;
import lichen.migration.config.MigratorConfig;
import org.junit.Test;

import java.io.InputStream;

/**
 * xml loader test
 * @author jcai
 */
public class XmlLoaderTest {
    @Test
    public void test_xml() throws Throwable {
        MigratorConfig config = XmlLoader.parseXML(MigratorConfig.class,
                getClass().getResourceAsStream("/test_config.xml"),
                Option.Some(getClass().getResourceAsStream("/migrator-config.xsd")));
        Assert.assertEquals("org.h2.Driver",config.driverClassName);
        Assert.assertEquals("lichen.migration.testdb",config.migratePackage);
    }
}
