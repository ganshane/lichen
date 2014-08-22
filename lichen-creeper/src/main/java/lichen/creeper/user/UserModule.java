package lichen.creeper.user;

import java.util.List;

import lichen.creeper.core.models.CreeperMenu;
import lichen.creeper.core.models.CreeperModuleDef;
import lichen.creeper.core.services.CreeperModuleManager;
import lichen.creeper.core.services.MenuSource;
import lichen.creeper.user.internal.UserServiceImpl;
import lichen.creeper.user.pages.admin.RoleForm;
import lichen.creeper.user.pages.admin.RoleList;
import lichen.creeper.user.pages.admin.UserList;
import lichen.creeper.user.services.UserSavedListener;
import lichen.creeper.user.services.UserService;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.services.PageRenderLinkSource;

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
    public static void provideMenu(Configuration<CreeperMenu> configuration,PageRenderLinkSource pageRenderLinkSource){
//		configuration.add(CreeperMenu.createCreeperMenu("user", "/user").title("用户").order(1).type(CreeperMenu.MENU_VIRTUAL));
		configuration.add(CreeperMenu.createCreeperMenu("user.role", "/admin/role").title("用户管理").order(1).type(CreeperMenu.MENU_VIRTUAL).authentication(true));
		configuration.add(CreeperMenu.createCreeperMenu("user.list", UserList.class).title("查询用户").order(1).authentication(true));
		configuration.add(CreeperMenu.createCreeperMenu("user.role.form", RoleForm.class).title("添加角色").order(2).authentication(true));
		configuration.add(CreeperMenu.createCreeperMenu("user.role.list", RoleList.class).title("查询角色").order(3).authentication(true));
    }

    @Contribute(value = CreeperModuleManager.class)
    public static void provideModule(Configuration<CreeperModuleDef> configuration){
        CreeperModuleDef def = CreeperModuleDef.create("用户","lichen.creeper.user");
        def.addPermissions("删除用户","编辑用户");
    	configuration.add(def);
    }
    public static UserSavedListener buildUserSavedListener(List<UserSavedListener> configuration,ChainBuilder chainBuilder){
        return chainBuilder.build(UserSavedListener.class,configuration);
    }
}
