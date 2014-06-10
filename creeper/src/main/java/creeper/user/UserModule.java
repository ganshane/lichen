package creeper.user;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.annotations.Contribute;

import creeper.core.models.CreeperMenu;
import creeper.core.services.CreeperModuleManager;
import creeper.core.services.MenuSource;

/**
 * 用户模块
 * @author shen
 *
 */
public class UserModule {
	
	@Contribute(MenuSource.class)
    public static void provideMenu(Configuration<CreeperMenu> configuration){
		configuration.add(new CreeperMenu("user","用户管理","/user",1));
		configuration.add(new CreeperMenu("user.regist","注册","/user/regist",1));
		configuration.add(new CreeperMenu("user.query","查询","/user/query",2));
    }

    @Contribute(value = CreeperModuleManager.class)
    public static void provideModule(Configuration<String> configuration){
    	configuration.add("creeper.user");
    }
}
