package lichen.creeper.user.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import lichen.creeper.user.entities.Role;

import java.util.UUID;

/**
 * 
 * @author shen
 *
 */
@RepositoryDefinition(domainClass = Role.class, idClass = UUID.class)
public interface RoleDao extends CrudRepository<Role, UUID>,
		JpaSpecificationExecutor<Role>,QueryDslPredicateExecutor<Role>  {

}
