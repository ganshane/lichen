package creeper.test.dao;

import creeper.test.entities.EntityA;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jcai
 */
@RepositoryDefinition(domainClass = EntityA.class,idClass = Long.class)
public interface EntityTestDao extends CrudRepository<EntityA, Long> {
    public EntityA findByAccountId(Long id);
    @Query("select o from EntityA o where o.accountId=?1")
    public List<EntityA> findByCustomQuery(Long id);
    @Transactional
    public EntityA save(EntityA accountInfo);
}
