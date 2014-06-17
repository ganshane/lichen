package creeper.test.dao;

import creeper.test.entities.EntityA;
import creeper.test.entities.EntityB;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jcai
 */
@RepositoryDefinition(domainClass = EntityB.class,idClass = String.class)
public interface EntityBDao extends CrudRepository<EntityB, String> {
}
