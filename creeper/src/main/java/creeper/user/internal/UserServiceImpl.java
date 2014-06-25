package creeper.user.internal;

import javax.inject.Inject;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import creeper.core.services.CreeperException;
import creeper.user.dao.UserDao;
import creeper.user.entities.User;
import creeper.user.services.UserService;

/**
 * 用户注册的实现类
 * @author jcai
 */
public class UserServiceImpl implements UserService{
	
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
    @Inject
    private UserDao _userDao;
    @Inject
    private PasswordService _passwordService;
    @Inject
    private Subject _subject;
    @Override
    public void register(User user) {
        //加密密码
        String passwordEncrypted = _passwordService.encryptPassword(user.getPass());
        user.setPass(passwordEncrypted);
        _userDao.save(user);
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
}
