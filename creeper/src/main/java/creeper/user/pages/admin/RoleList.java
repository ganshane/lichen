package creeper.user.pages.admin;

import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import creeper.user.dao.RoleDao;
import creeper.user.entities.Role;
import creeper.user.services.UserService;

/**
 * 
 * @author shen
 *
 */
public class RoleList {
	
	private static Logger logger = LoggerFactory.getLogger(RoleList.class);
	
	@Property
	private Role role;
	
	@Property
	private Role roleParams;
	
	@Inject
	private UserService _userService;
	
	@Inject
	private RoleDao _roleDao;
	
	public List<Role> getRoles(){
		return _userService.findAllRole(roleParams);
	}
	
	void onActivate(){
		if(roleParams == null)
			roleParams = new Role(); 
	}
	
	void onActivate(Role role){

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
