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
        MigratorConfig config = XmlLoader.parseXML(MigratorConfig.class, getClass().getResourceAsStream("/test_config.xml"), Option.<InputStream>None());
        Assert.assertEquals("driver_class_name",config.driverClassName);
        Assert.assertEquals("lichen.migration.testdb",config.migratePackage);
    }
}
