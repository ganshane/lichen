package lichen.creeper.user.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.mysema.query.types.ExpressionUtils;

import lichen.creeper.user.dao.RoleDao;
import lichen.creeper.user.dao.UserDao;
import lichen.creeper.user.entities.QRole;
import lichen.creeper.user.entities.QUser;
import lichen.creeper.user.entities.Role;
import lichen.creeper.user.entities.User;
import lichen.creeper.user.services.UserSavedListener;
import lichen.creeper.user.services.UserService;

/**
 * 用户注册的实现类
 * @author jcai
 */
public class UserServiceImpl implements UserService{
	
    
    @Inject
    private UserDao _userDao;
    
    @Inject
    private RoleDao _roleDao;
    
    @Inject
    private PasswordService _passwordService;
    @Inject
    private Subject _subject;
    @Inject
    private UserSavedListener userSavedListener;
    @Override
    public void register(User user) {
        //加密密码
        String passwordEncrypted = _passwordService.encryptPassword(user.getPass());
        user.setPass(passwordEncrypted);
        _userDao.save(user);
        userSavedListener.afterSaved(user);
    }

    @Override
    public void login(String name, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        token.setRememberMe(true);
        //TODO catch exception to show message
//        try{
        	_subject.login(token);
//        }catch(UnknownAccountException e){
//        	//user do not exist
//        }catch(IncorrectCredentialsException e){
//        	//invalid password
//        }catch( AuthenticationException e){
//        	throw CreeperException.wrap(e);
//        }
        //TODO 保存用户相关信息
    }

    @Override
    public void logout() {
        //TODO 保存用户相关信息
        _subject.logout();
    }

	@Override
	public void saveOrUpdate(Role role) {
		_roleDao.save(role);
	}
	
	@Override
	public Iterable<Role> findAllRole(final Role role) {
		if(null != role){
			QRole r = QRole.role;
			Collection<com.mysema.query.types.Predicate> list = new ArrayList<com.mysema.query.types.Predicate>();
			if(null != role.getId()){list.add(r.id.eq(role.getId()));}
			if(null != role.getName()){list.add(r.name.eq(role.getName()));}
			return _roleDao.findAll(ExpressionUtils.allOf(list));
		}
		return null;
	}
	
	@Override
	public Page<User> findAll(User user,Pageable pageable){
		if(null != user){
			QUser u = QUser.user;
			Collection<com.mysema.query.types.Predicate> list = new ArrayList<com.mysema.query.types.Predicate>();
			if(null != user.getName()){list.add(u.name.eq(user.getName()));}
			if(null != user.getMail()){list.add(u.mail.eq(user.getMail()));}
			
			Page<User> result = _userDao.findAll(ExpressionUtils.allOf(list), pageable);
			return result;
		}
		return null;
	}
	
	@Override
	public List<User> findAll(final User user) {
		if(null != user)
			return _userDao.findAll(new Specification<User>() {
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
		return null;
	}

	@Override
	public void saveOrUpdate(User user) {
		_userDao.save(user);
        userSavedListener.afterSaved(user);
	}
}