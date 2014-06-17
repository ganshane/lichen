package creeper.user.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import creeper.user.dao.UserDao;
import creeper.user.entities.User;

/**
 * 用户注册
 * @author shen
 *
 */
public class UserRegist {
	
	private static Logger logger = LoggerFactory.getLogger(UserRegist.class);
	
	@Inject
	private UserDao userDao;
	
	@Property
	private User user;
	
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	//初始化user实体
	void onPrepare(){
		user = new User();
	}
	
	@OnEvent(value=EventConstants.SUBMIT,component="registUserForm")
	Object onSaveUser(){
		logger.debug("saveuser");
		userDao.save(user);
		return pageRenderLinkSource.createPageRenderLinkWithContext(UserList.class, new Object[]{user.getId(),user.getName(),user.getPass()});
	}
}
