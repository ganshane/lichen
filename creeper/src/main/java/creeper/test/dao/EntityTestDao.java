package creeper.test.dao;

import creeper.test.entities.EntityA;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jcai
 */
@RepositoryDefinition(domainClass = EntityA.class,idClass = Long.class)
public interface EntityTestDao {
    public EntityA findByAccountId(Long id);
    @Transactional
    public EntityA save(EntityA accountInfo);
}
