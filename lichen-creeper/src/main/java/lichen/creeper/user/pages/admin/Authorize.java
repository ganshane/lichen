package lichen.creeper.user.pages.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import lichen.creeper.core.models.CreeperModuleDef;
import lichen.creeper.core.services.CreeperModuleManager;
import lichen.creeper.user.entities.Permission;
import lichen.creeper.user.entities.Role;
import lichen.creeper.user.services.UserService;

/**
 * 授权页
 * @author shen
 *
 */
@RequiresUser
public class Authorize {
	
	@Property
	@PageActivationContext
	private Role role;
	
	@Property
	private CreeperModuleDef module;
	
	@SuppressWarnings("unused")
	@Property
	private String permission;
	
	@Inject
	private UserService _userService;
	
	@Inject
	private CreeperModuleManager creeperModuleManager;
	
	@Inject
	private Request request;

	@Property
	private String permisson;
	
	
	public Boolean getHasPermission(){
		for(Permission p : role.getPermissions()){
			if(permisson.equals(p.getPermission()))
				return true;
		}
		return false;
	}
	
	@Cached
	public Iterable<CreeperModuleDef> getModules(){
		return new Iterable<CreeperModuleDef>(){
			@Override
			public Iterator<CreeperModuleDef> iterator() {
				return creeperModuleManager.getAllModules();
			}};
	}
	
	public List<String> getPermissions(){
		List<String> pes = module.getPermissions();
		if(null != pes){
			return pes;
		}else{
			return new ArrayList<String>();
		}
	}
	
	@OnEvent(value=EventConstants.SUCCESS,component="authorizeForm")
	void doAuthorizeRole(){
		String[] perm = request.getParameters("perm");
		//Arrays.asList() 返回java.util.Arrays$ArrayList， 而不是ArrayList,不能remove操作。
		List<String> permlist = new ArrayList<String>(Arrays.asList(perm));
		Set<Permission> permissions = role.getPermissions();
		//已存在权限
		List<String> existPerms = new ArrayList<String>();
		//取消的权限
		Set<Permission> depPermissions = new HashSet<Permission>();
		for(Permission p : permissions){
			if(permlist.contains(p.getPermission())){
				existPerms.add(p.getPermission());
			}else{
				depPermissions.add(p);
			}
		}
		permissions.removeAll(depPermissions);
		permlist.removeAll(existPerms);
		//新加的权限
		for(String per : permlist){
			Permission p = new Permission();
			p.setRole(role);
			p.setPermission(per);
			permissions.add(p);
		}
		_userService.saveOrUpdate(role);
	}
	
}
