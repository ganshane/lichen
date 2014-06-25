package creeper.user.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import creeper.user.entities.Role;

/**
 * 
 * @author shen
 *
 */
public interface RoleService {
	
	/**
	 * 添加新角色
	 * @param role
	 */
    @Transactional
    public void saveOrUpdateRole(Role role);
    
    /**
     * 根据role里封装的参数，参数符合条件的所有记录。
     * @param role
     * @return
     */
    public List<Role> findAll(Role role);
	
}
