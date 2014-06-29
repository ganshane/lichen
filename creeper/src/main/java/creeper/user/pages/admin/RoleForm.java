package creeper.user.pages.admin;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import creeper.user.entities.Role;
import creeper.user.services.RoleService;

/**
 * 
 * @author shen
 *
 */
public class RoleForm {
	
	@Inject
	private RoleService _roleService;
	
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
		_roleService.saveOrUpdateRole(role);
		return pageRenderLinkSource.createPageRenderLinkWithContext(RoleList.class, role);
	}
}
