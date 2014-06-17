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

public class UserUpdate {
	
	private static Logger logger = LoggerFactory.getLogger(UserUpdate.class);
	
	@Property
	private User user;
	
	@Inject
	private UserDao userDao;
	
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	/**
	 * 调用2次。
	 * 第一次从list页面的pagelink直接过来，接受pagelink的context参数。调用完后，清空页面前，调用onPassivate方法，把user实体传走。当点击
	 * 更新按钮再次提交到update页面的时候，传给第二次调用的onActivate方法，如果不写onPassivate，就不会有onActivate的第二次调用，传来的实
	 * 体是没有主键的，不是一个，所以不能进行更新操作。
	 * 
	 * 第二次调用就是写了onPassivate方法的时候才会调，接受进行更新的实体。
	 * 
	 * 接受从页面context传来的参数。
	 * @param user
	 */
	void onActivate(User user){
		logger.debug("--onActivate"+user.getId());
		if(null != user){
			this.user = user;
		}
	}
	
	/**
	 * Tapestry5的使用了页面池技术，页面在每次渲染前都是从页面池中随机获取一个页面，而从页面池中取得的页面，所有的属性都是被清空了的。
	 * 在清空属性前会首先查看是否包含onPassivate(是没有参数的)方法，如果有，就把其返回值保存起来，然后从页面池中取得页面后，再把刚才
	 * 保存的值作为参数传递给onActivate方法。
	 * @return
	 */
	Object onPassivate(){
		logger.debug("--onPassivate");
		return user;
	}
	
	//页面加载时运行,该方法不能有参数。在onActivate后执行。
	void onPrepare(){
		logger.debug("-----onPrepare");
	}
	
	@OnEvent(value=EventConstants.SUBMIT,component="userUpdateForm")
	Object onUpdateUser(){
		//只有真正进到这个@OnEvent方法里的时候，user才会变成页面上form里最后修改后的封装后的实体。
		logger.debug("--"+user.getId());
		userDao.save(user);
		return pageRenderLinkSource.createPageRenderLinkWithContext(UserList.class, new Object[]{user.getId(),user.getName(),user.getPass()});
	}
	
}
