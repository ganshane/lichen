package creeper.test;

import creeper.core.models.CreeperMenu;
import creeper.core.services.MenuSource;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.annotations.Contribute;

import java.util.Collection;

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
    }
}
