package creeper.user.pages;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

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
	
	void onActivate(final String id,final String name,final String password){
		users = userDao.findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(null != id){list.add(cb.equal(root.get("id"),id));}
				if(null != name){list.add(cb.equal(root.get("name"),name));}
				if(null != password){list.add(cb.equal(root.get("pass"),password));}
				Predicate[] p = new Predicate[list.size()];   
				return cb.and(list.toArray(p));
			}
        });
	}
	
//	单击eventlink执行删除操作
	@OnEvent(value="del")
	Object onDeleteUser(User user){
		userDao.delete(user);
		return this;
	}
	
}
