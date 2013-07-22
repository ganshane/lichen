package lichen.migration.internal;

import junit.framework.Assert;
import lichen.migration.services.Options;
import org.junit.Test;

/**
 *
 * @author jcai
 */
public class ColumnDefinitionTest {
    @Test
    public void test_autoincrement(){
        A a = new A();
        a.initialize();
        Assert.assertTrue(a.isAutoIncrement);
        Assert.assertEquals(a.options.size(),0);
    }
    @ColumnSupportsAutoIncrement
    class A extends ColumnDefinition{
        public A(){
            Options optionsService = new OptionsImpl();
            options.add(optionsService.AutoIncrement());
            options.add(optionsService.AutoIncrement());
        }

        @Override
        protected String sql() {
            return null;
        }
    }
}
