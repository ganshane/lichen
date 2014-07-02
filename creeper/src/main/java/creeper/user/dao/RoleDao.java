package creeper.user.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import creeper.user.entities.Role;

/**
 * 
 * @author shen
 *
 */
@RepositoryDefinition(domainClass = Role.class, idClass = String.class)
public interface RoleDao extends CrudRepository<Role, String>,
		JpaSpecificationExecutor<Role>,QueryDslPredicateExecutor<Role>  {

}
