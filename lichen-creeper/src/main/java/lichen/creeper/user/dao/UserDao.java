package lichen.creeper.user.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import lichen.creeper.user.entities.User;

import java.util.UUID;

/**
 * 
 * @author shen
 *
 */
@RepositoryDefinition(domainClass = User.class,idClass = UUID.class)
public interface UserDao extends CrudRepository<User, UUID> ,JpaSpecificationExecutor<User> , QueryDslPredicateExecutor<User> {
    public User findByName(String name);
}
