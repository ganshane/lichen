package creeper.user.pages;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.data.jpa.domain.Specification;

import creeper.user.dao.UserDao;
import creeper.user.entities.User;

public class UserList{
	
	@SuppressWarnings("unused")
	@Property
	private User user;
	
	@SuppressWarnings("unused")
	@Property
	private List<User> users;
	
	@Inject
	private UserDao userDao;
	
	void onActivate(final User user){
		if(null != user){
			users = userDao.findAll(new Specification<User>() {
				@Override
				public Predicate toPredicate(Root<User> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<Predicate>();
					if(null != user.getId()){list.add(cb.equal(root.get("id"),user.getId()));}
					if(null != user.getName()){list.add(cb.equal(root.get("name"),user.getName()));}
					if(null != user.getPass()){list.add(cb.equal(root.get("pass"),user.getPass()));}
					Predicate[] p = new Predicate[list.size()];   
					return cb.and(list.toArray(p));
				}
	        });
		}
	}
	
//	单击eventlink执行删除操作
	@OnEvent(value="del")
	Object onDeleteUser(User user){
		userDao.delete(user);
		return this;
	}
	
}
