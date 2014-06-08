package creeper.core.services.jpa;

import creeper.core.config.CreeperCoreConfig;
import creeper.core.internal.CreeperModuleManagerImpl;
import creeper.core.services.CreeperModuleManager;
import creeper.core.services.db.DatabaseMigrationModule;
import org.apache.shiro.util.ThreadContext;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.hibernate.cfg.Environment;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基础类，用于对实体和dao的测试
 * @author jcai
 */
public abstract class BaseEntityTestCase {
    protected Registry registry;
    protected Class<?>[] getIocModules(){ return null;}
    protected String[] getCreeperModules(){return null;}
    @Before
    public void setup(){
        ThreadContext.put("creeper.modules", getCreeperModules());
        List<Class<?>> all = new ArrayList<Class<?>>();
        if(getIocModules()!= null){
            all.addAll(Arrays.asList(getIocModules()));
        }
        all.add(DatabaseMigrationModule.class);
        all.add(CreeperJpaModule.class);
        all.add(TestDatabaseModule.class);
        registry = RegistryBuilder.buildAndStartupRegistry(all.toArray(new Class<?>[all.size()]));
    }
    @After
    public void teardown(){
        registry.shutdown();
        ThreadContext.remove("creeper.modules");
    }
    public static class TestDatabaseModule{
        public static void bind(ServiceBinder binder){
            binder.bind(CreeperModuleManager.class, CreeperModuleManagerImpl.class);
        }
        @Contribute(CreeperModuleManager.class)
        public static void provideTestModule(Configuration<String> modules){
            String [] testModules = (String[]) ThreadContext.get("creeper.modules");
            if(testModules !=null)
                for(String m:testModules){
                    modules.add(m);
                }
        }
        public static CreeperCoreConfig buildConfig(){
            CreeperCoreConfig config = new CreeperCoreConfig();
            config.db._driverClassName="org.h2.Driver";
            config.db._url="jdbc:h2:mem:testdb";
            config.db._username = "sa";
            CreeperCoreConfig.JpaProperty property = new CreeperCoreConfig.JpaProperty();
            /*
            property.name = Environment.HBM2DDL_AUTO;
            property.value = "create";
            config.jpaProperties.add(property);
            */

            property = new CreeperCoreConfig.JpaProperty();
            property.name = Environment.SHOW_SQL;
            property.value = "true";
            config.jpaProperties.add(property);

            return config;
        }
    }
}
