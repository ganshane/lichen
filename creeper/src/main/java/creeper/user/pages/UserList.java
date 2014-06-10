package creeper.user.pages;

import java.util.List;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import creeper.user.dao.UserDao;
import creeper.user.entities.User;

public class UserList{
	
	private static Logger logger = LoggerFactory.getLogger(UserList.class);
	
	@SuppressWarnings("unused")
	@Property
	private User user;
	
	@SuppressWarnings("unused")
	@Property
	private List<User> users;
	
	@Inject
	private UserDao userDao;
	
	void onActivate(Long id,String name,String username,String password){
		users = userDao.findByCustomQuery(id, name, username, password);
	}
	
//	单击eventlink执行删除操作
	@OnEvent(value="del")
	Object onDeleteUser(User user){
		userDao.delete(user);
		return this;
	}
	
}
