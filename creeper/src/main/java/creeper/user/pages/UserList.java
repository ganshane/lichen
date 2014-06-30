package creeper.user.pages;

import java.util.List;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import creeper.user.dao.UserDao;
import creeper.user.entities.User;
import creeper.user.services.UserService;

public class UserList{
	
	@SuppressWarnings("unused")
	@Property
	private User user;
	
	@Property
	private User userParams;
	
	@Inject
	private UserService _userService;
	
	@Inject
	private UserDao userDao;
	
	public List<User> getUsers(){
		return _userService.findAll(userParams);
	}
	
	void onActivate(final User user){
		if(userParams == null)
			userParams = new User(); 
	}
	
//	单击eventlink执行删除操作
	@OnEvent(value="del")
	Object onDeleteUser(User user){
		userDao.delete(user);
		return this;
	}
	
}
