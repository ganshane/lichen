package creeper.user.pages.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.services.Request;

import creeper.user.dao.RoleDao;
import creeper.user.dao.UserDao;
import creeper.user.entities.Role;
import creeper.user.entities.User;
import creeper.user.services.UserService;

/**
 * 用户管理操作页面
 * @author shen
 *
 */
public class UserManager {
	
	@Property
	private User user;
	
	@Property
	private Role role;
	
	@Inject
	private UserService _userService;
	
	@Inject
	private UserDao _userDao;
	
	@Inject
	private RoleDao _roleDao;
	
	@Inject
	private Request request;
	
	public Boolean getHasRole(){
		for(Role ur : user.getRoles()){
			if(role.getId().equals(ur.getId()))
				return true;
		}
		return false;
	}
	
	public Iterable<Role> getRoles(){
		return _userService.findAllRole(new Role());
	}
	
	void onActivate(User user){
		if(null != user){
			this.user = user;
		}
	}
	
	Object onPassivate(){
		return user;
	}
	
	@OnEvent(value=EventConstants.SUBMIT,component="userUpdateForm")
	void onUpdateUser(){
		String[] _user_role = request.getParameters("_user_role");
		//Arrays.asList() 返回java.util.Arrays$ArrayList， 而不是ArrayList,不能remove操作。
		List<String> rolelist = new ArrayList<String>(Arrays.asList(_user_role));
		Set<Role> userroles = user.getRoles();
		//已存在角色
		List<String> existRoles = new ArrayList<String>();
		//取消的角色
		Set<Role> depRoles = new HashSet<Role>();
		for(Role r : userroles){
			if(rolelist.contains(r.getId())){
				existRoles.add(r.getId());
			}else{
				depRoles.add(r);
			}
		}
		userroles.removeAll(depRoles);
		rolelist.removeAll(existRoles);
		//新加的权限
		for(String nr : rolelist){
			Role newRole = _roleDao.findOne(nr);
			userroles.add(newRole);
		}
		_userDao.save(user);
	}

}
