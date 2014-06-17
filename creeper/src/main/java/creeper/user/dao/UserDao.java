package creeper.user.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import creeper.user.entities.User;

/**
 * 
 * @author shen
 *
 */
@RepositoryDefinition(domainClass = User.class,idClass = String.class)
public interface UserDao extends CrudRepository<User, String> ,JpaSpecificationExecutor<User> {

}
