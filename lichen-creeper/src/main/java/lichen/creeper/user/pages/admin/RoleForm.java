package lichen.creeper.user.pages.admin;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import lichen.creeper.user.entities.Role;
import lichen.creeper.user.services.UserService;

/**
 * 
 * @author shen
 *
 */
public class RoleForm {
	
	@Inject
	private UserService _userService;
	
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	@Property
	private Role role;
	
	void onActivate(){
		if(null == role)
			role = new Role();
	}
	
	@OnEvent(value=EventConstants.SUBMIT,component="roleForm")
	Object doSaveRole(){
		_userService.saveOrUpdate(role);
		return pageRenderLinkSource.createPageRenderLinkWithContext(RoleList.class, role);
	}
}
