package lichen.creeper.user.pages.admin;

import lichen.creeper.core.annotations.Initialize;
import lichen.creeper.core.components.Pagination;
import lichen.creeper.user.entities.User;
import lichen.creeper.user.pages.BasePage;
import lichen.creeper.user.services.UserService;

import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UserList extends BasePage{
	
	@SuppressWarnings("unused")
	@Property
	private User user;
	
	@Property
	@Initialize
	private User userParams;
	
	@Inject
	private UserService _userService;
	
	@InjectComponent
	private Pagination<?> pagination;
	
	@Cached
	public Page<User> getUsers(){
		Pageable pageable = pagination.getSelectedPage();
		return _userService.findAll(userParams, pageable);
	}
	
	@BeginRender
	void setupParameter(){
		//优先从参数开始读取查询对象
		User tmp =  pagination.getRequestParameter(User.class, 1);
		if(tmp != null)
			userParams = tmp;
		
		//参数为空，则进行构造
		if(userParams == null)
			userParams = new User();
		
	}
	
}
