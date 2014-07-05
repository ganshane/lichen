package lichen.creeper.user.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import org.apache.tapestry5.services.PageRenderLinkSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lichen.creeper.core.components.CreeperForm;
import lichen.creeper.user.entities.User;
import lichen.creeper.user.pages.admin.UserList;
import lichen.creeper.user.services.UserService;

/**
 * 用户登录
 * @author shen
 *
 */
public class UserLogin {
	
	private static Logger logger = LoggerFactory.getLogger(UserLogin.class);
	
	@Inject
	private UserService userService;
	
	@Property
	private User user;
	
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	@Component
	private CreeperForm loginUserForm;
	
	//初始化user实体
	void onPrepare(){
		user = new User();
	}
	

	@OnEvent(value=EventConstants.VALIDATE,component="loginUserForm")
	void onAuth(){
		try{
			userService.login(user.getName(), user.getPass());
		}catch(Exception e){
			loginUserForm.getInnerForm().recordError(e.getMessage());
		}
		
	}
	@OnEvent(value=EventConstants.SUCCESS,component="loginUserForm")
	Object onLoginUser(){
		logger.debug("saveuser");
		
		return pageRenderLinkSource.createPageRenderLinkWithContext(UserList.class, new Object[]{user.getId(),user.getName(),user.getPass()});
	}
}
