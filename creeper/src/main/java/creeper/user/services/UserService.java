package creeper.user.services;

import java.util.List;

import creeper.user.entities.Role;
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

    /**
     * 注销
     */
    @Transactional
    public void logout();
    
    /**
	 * 添加新角色
	 * @param role
	 */
    @Transactional
    public void saveOrUpdate(Role role);
    
    /**
     * 根据role里封装的参数，参数符合条件的所有记录。
     * @param role
     * @return
     */
    public List<Role> findAll(Role role);
    
    /**
     * 根据user里封装的参数，参数符合条件的所有记录。
     * @param user
     * @return
     */
	public List<User> findAll(User user);
}
