package lichen.creeper.test;

import lichen.creeper.core.models.CreeperMenu;
import lichen.creeper.core.models.CreeperModuleDef;
import lichen.creeper.core.services.CreeperModuleManager;
import lichen.creeper.core.services.MenuSource;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.annotations.Contribute;

/**
 * 测试使用的package
 * @author jcai
 */
public class CreeperTestModule {
    @Contribute(MenuSource.class)
    public static void provideMenu(Configuration<CreeperMenu> coll){
//        coll.add(new CreeperMenu("test","测试模块","/test",1));
//        coll.add(new CreeperMenu("test.a","测试A","/test/a",1));
//        coll.add(new CreeperMenu("test.b","测试B","/test/b",1));
//
//        coll.add(new CreeperMenu("test2","测试模块2","/test2",1));
//        coll.add(new CreeperMenu("test.a2","测试A2","/test2/a2",1));
//        coll.add(new CreeperMenu("test.b2","测试B2","/test2/b2",1));
        
    }

    @Contribute(value = CreeperModuleManager.class)
    public static void provideModule(Configuration<CreeperModuleDef> configuration){
    	configuration.add(CreeperModuleDef.create("测试","lichen.creeper.test"));
    }
}
