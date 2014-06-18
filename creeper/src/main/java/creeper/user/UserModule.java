package creeper.user;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;

import creeper.core.models.CreeperMenu;
import creeper.core.services.CreeperModuleManager;
import creeper.core.services.MenuSource;
import creeper.user.internal.UserServiceImpl;
import creeper.user.services.UserService;

/**
 * 用户模块
 * @author shen
 *
 */
public class UserModule {
	
	public static void bind(ServiceBinder binder){
        binder.bind(UserService.class, UserServiceImpl.class);
    }
	
	@Contribute(MenuSource.class)
    public static void provideMenu(Configuration<CreeperMenu> configuration){
		configuration.add(new CreeperMenu("user","用户管理","/user",1));
		configuration.add(new CreeperMenu("user.regist","注册","/user/regist",1));
		configuration.add(new CreeperMenu("user.login","登录","/user/login",2));
		configuration.add(new CreeperMenu("user.query","查询","/user/query",3));
    }

    @Contribute(value = CreeperModuleManager.class)
    public static void provideModule(Configuration<String> configuration){
    	configuration.add("creeper.user");
    }
}
