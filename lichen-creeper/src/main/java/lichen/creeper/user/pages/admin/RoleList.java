package lichen.creeper.user.pages.admin;

import lichen.creeper.core.annotations.Initialize;
import lichen.creeper.core.components.Pagination;
import lichen.creeper.user.entities.Role;
import lichen.creeper.user.services.UserService;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 
 * @author shen
 *
 */
@RequiresUser
//@RequiresRoles(value = { "aaa" })
public class RoleList {
	
	@SuppressWarnings("unused")
	@Property
	private Role role;
	
	@Property
	@Initialize
	private Role roleParams;
	
	@Inject
	private UserService _userService;
	
	@InjectComponent
	private Pagination<?> pagination;
	
	@Cached
	public Page<Role> getRoles(){
		Pageable pageable = pagination.getSelectedPage();
		return _userService.findAll(roleParams, pageable);
	}
	
	@BeginRender
	void setupParameter(){
		//优先从参数开始读取查询对象
		Role tmp =  pagination.getRequestParameter(Role.class, 1);
		if(tmp != null)
			roleParams = tmp;

//		不能删，删了直接点报错，worker到底拦截哪里
		//参数为空，则进行构造
		if(roleParams == null)
			roleParams = new Role();
	}
	
}
