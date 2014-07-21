package lichen.creeper.user.dao;

import java.util.UUID;

import lichen.creeper.user.entities.User;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * Repository，是spring-data-jpa 实现了一组CRUD相关的方法。
 * JpaSpecificationExecutor： 比较特殊，不属于Repository体系，是spring-data-jpa实现一组JPA Criteria查询相关的
 * QueryDslPredicateExecutor 是spring-data-jpa中使用querydsl的接口。
 * @author shen
 *
 */
@RepositoryDefinition(domainClass = User.class,idClass = UUID.class)
public interface UserDao extends CrudRepository<User, UUID> ,JpaSpecificationExecutor<User> ,QueryDslPredicateExecutor<User>{
    public User findByName(String name);
}
