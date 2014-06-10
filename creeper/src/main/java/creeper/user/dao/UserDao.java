package creeper.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import creeper.user.entities.User;

/**
 * 
 * @author shen
 *
 */
@RepositoryDefinition(domainClass = User.class,idClass = Long.class)
public interface UserDao extends CrudRepository<User, Long> {
    
    @Query("select o from User o where o.id=?1 and o.name=?2 and o.username=?3 and o.password=?4")
    public List<User> findByCustomQuery(Long id,String name,String username,String password);

}
