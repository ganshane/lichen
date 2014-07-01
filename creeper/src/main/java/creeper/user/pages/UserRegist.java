package creeper.user.pages;

import java.util.Date;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import creeper.core.components.CreeperForm;
import creeper.user.entities.User;
import creeper.user.services.UserService;

/**
 * 用户注册
 * @author shen
 *
 */
public class UserRegist {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UserRegist.class);
	
	@Inject
	private UserService userService;
	
	@Property
	private User user;
	
	@Property
	private String repeatPassWord;
	
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	@Component
	private CreeperForm registUserForm;
	
	//初始化user实体
	void onPrepare(){
		user = new User();
	}
	
	@OnEvent(value=EventConstants.VALIDATE,component="registUserForm")
	void doValid(){
		if(!repeatPassWord.equals(user.getPass()))
			registUserForm.getInnerForm().recordError("两次输入的密码不一致！");
	}
	
	@OnEvent(value=EventConstants.SUCCESS,component="registUserForm")
	Object doSaveUser(){
		user.setCreated(new Date());
		userService.register(user);
		return pageRenderLinkSource.createPageRenderLinkWithContext(UserLogin.class);
	}
	
}
