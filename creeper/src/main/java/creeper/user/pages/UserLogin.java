package creeper.user.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import creeper.user.entities.User;
import creeper.user.services.UserService;

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
	
	//初始化user实体
	void onPrepare(){
		user = new User();
	}
	
	@OnEvent(value=EventConstants.SUBMIT,component="loginUserForm")
	Object onLoginUser(){
		logger.debug("saveuser");
		userService.login(user.getName(), user.getPass());
		return pageRenderLinkSource.createPageRenderLinkWithContext(UserList.class, new Object[]{user.getId(),user.getName(),user.getPass()});
	}
}
