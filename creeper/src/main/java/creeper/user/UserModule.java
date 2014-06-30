package creeper.user;

import creeper.user.pages.UserRegist;
import creeper.user.services.UserSavedListener;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;

import creeper.core.models.CreeperMenu;
import creeper.core.models.CreeperModuleDef;
import creeper.core.services.CreeperModuleManager;
import creeper.core.services.MenuSource;
import creeper.user.internal.RoleServiceImpl;
import creeper.user.internal.UserServiceImpl;
import creeper.user.services.RoleService;
import creeper.user.services.UserService;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

/**
 * 用户模块
 * @author shen
 *
 */
public class UserModule {
	
	public static void bind(ServiceBinder binder){
        binder.bind(UserService.class, UserServiceImpl.class);
        binder.bind(RoleService.class, RoleServiceImpl.class);
    }
	
	@Contribute(MenuSource.class)
    public static void provideMenu(Configuration<CreeperMenu> configuration,PageRenderLinkSource pageRenderLinkSource){
		configuration.add(new CreeperMenu("user","用户","/user",1, CreeperMenu.MENU_VIRTUAL));
		configuration.add(new CreeperMenu("user.regist","注册",UserRegist.class,1));
		configuration.add(new CreeperMenu("user.login","登录","/user/login",2));
		configuration.add(new CreeperMenu("user.query","查询","/user/query",3));
		
		configuration.add(new CreeperMenu("user.role","角色管理","/admin/user",4, CreeperMenu.MENU_VIRTUAL));
		configuration.add(new CreeperMenu("user.role.form","添加角色","/admin/user/roleform",1)); //  /admin/user/roleform
		configuration.add(new CreeperMenu("user.role.list","查询角色","/admin/user/rolelist",2));
    }

    @Contribute(value = CreeperModuleManager.class)
    public static void provideModule(Configuration<CreeperModuleDef> configuration){
        CreeperModuleDef def = CreeperModuleDef.create("用户","creeper.user");
        def.addPermissions("删除用户","编辑用户");
    	configuration.add(def);
    }
    public static UserSavedListener buildUserSavedListener(List<UserSavedListener> configuration,ChainBuilder chainBuilder){
        return chainBuilder.build(UserSavedListener.class,configuration);
    }
}
