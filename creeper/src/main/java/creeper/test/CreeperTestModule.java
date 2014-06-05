package creeper.test;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.annotations.Contribute;

import creeper.core.config.CreeperCoreConfig;
import creeper.core.models.CreeperDbScript;
import creeper.core.models.CreeperMenu;
import creeper.core.services.DataBaseMigrationService;
import creeper.core.services.MenuSource;

/**
 * 测试使用的package
 * @author jcai
 */
public class CreeperTestModule {
    @Contribute(MenuSource.class)
    public static void provideMenu(Configuration<CreeperMenu> coll){
        coll.add(new CreeperMenu("test","测试模块","/test",1));
        coll.add(new CreeperMenu("test.a","测试A","/test/a",1));
        coll.add(new CreeperMenu("test.b","测试B","/test/b",1));
        
        coll.add(new CreeperMenu("test2","测试模块2","/test2",1));
        coll.add(new CreeperMenu("test.a2","测试A2","/test2/a2",1));
        coll.add(new CreeperMenu("test.b2","测试B2","/test2/b2",1));
    }
    
    @Contribute(value = DataBaseMigrationService.class)
    public static void provideDb(Configuration<CreeperDbScript> configuration, final CreeperCoreConfig creeperCoreConfig){
    	configuration.add(new CreeperDbScript("creeper.test.db",false));
    	configuration.add(new CreeperDbScript("creeper.test2.db",false));
    }
}
