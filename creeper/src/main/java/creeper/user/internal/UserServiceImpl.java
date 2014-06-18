package creeper.user.internal;

import creeper.user.dao.UserDao;
import creeper.user.entities.User;
import creeper.user.services.UserService;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.subject.Subject;

import javax.inject.Inject;

/**
 * 用户注册的实现类
 * @author jcai
 */
public class UserServiceImpl implements UserService{
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
        _subject.login(token);//TODO catch exception to show message
    }
}
