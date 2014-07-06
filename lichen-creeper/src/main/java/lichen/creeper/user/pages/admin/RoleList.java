package lichen.creeper.user.pages.admin;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import lichen.creeper.user.dao.RoleDao;
import lichen.creeper.user.entities.Role;
import lichen.creeper.user.services.UserService;

/**
 * 
 * @author shen
 *
 */
public class RoleList {
	
	@SuppressWarnings("unused")
	@Property
	private Role role;
	
	@Property
	private Role roleParams;
	
	@Inject
	private UserService _userService;
	
	@Inject
	private RoleDao _roleDao;
	
	public Iterable<Role> getRoles(){
		return _userService.findAllRole(roleParams);
	}
	
	void onActivate(){
		if(roleParams == null)
			roleParams = new Role(); 
	}
	
	void onActivate(Role role){
        if(roleParams == null)
            roleParams = new Role();
        this.role = role;
	}
	
//	单击eventlink执行删除操作
	@OnEvent(value="del")
	void doDeleteRole(Role role,Role param){
		this.roleParams = param;
		_roleDao.delete(role);
	}
	
	@OnEvent(value=EventConstants.SUBMIT,component="roleQueryForm")
	void doQueryRole(){

	}
		
}
