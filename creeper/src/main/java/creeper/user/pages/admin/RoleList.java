package creeper.user.pages.admin;

import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import creeper.user.dao.RoleDao;
import creeper.user.entities.Role;
import creeper.user.services.RoleService;

/**
 * 
 * @author shen
 *
 */
public class RoleList {
	
	@Property
	private Role role = new Role();
	
	@Property
	private List<Role> roles;
	
	@Inject
	private RoleService _roleService;
	
	@Inject
	private RoleDao _roleDao;
	
	void onActivate(Role role){
		roles = _roleService.findAll(role);
	}
	
//	单击eventlink执行删除操作
	@OnEvent(value="del")
	Object doDeleteRole(Role role){
		_roleDao.delete(role);
		return this;
	}
	
	@OnEvent(value=EventConstants.SUBMIT,component="roleQueryForm")
	void doQueryRole(){
		roles = _roleService.findAll(role);
	}
		
}
