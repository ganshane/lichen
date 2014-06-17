package creeper.user.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import creeper.user.entities.User;

public class UserQuery {
	
	private static Logger logger = LoggerFactory.getLogger(UserQuery.class);
	
	@Property
	private User user = new User();
	
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	@OnEvent(value=EventConstants.SUBMIT,component="queryUserForm")
	Object onQueryUser(){
		logger.debug(user.getName());
		return pageRenderLinkSource.createPageRenderLinkWithContext(UserList.class, new Object[]{user.getId(),user.getName(),user.getPass()});
	}
	
}
