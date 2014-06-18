package creeper.user.services;

import creeper.user.entities.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * 针对用户操作的服务类接口
 * @author jcai
 */
public interface UserService {
    /**
     * 注册新用户
     * @param user 前端传入的用户实例
     */
    @Transactional
    public void register(User user);

    /**
     * 通过用户名和密码进行登录系统
     * @param name 用户名
     * @param password 密码
     */
    @Transactional
    public void login(String name,String password);
}
