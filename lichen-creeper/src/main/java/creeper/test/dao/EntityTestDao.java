package creeper.test.dao;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import creeper.test.entities.EntityA;

/**
 * @author jcai
 */
@RepositoryDefinition(domainClass = EntityA.class,idClass = Long.class)
public interface EntityTestDao extends CrudRepository<EntityA, Long> {
    public EntityA findById(Long id);
    @Query("select o from EntityA o where o.id=?1")
    public List<EntityA> findByCustomQuery(Long id);
    @Query("select o.id from EntityA o where o.id=?1")
    public List<EntityA> findByIds(Long id);
    //动态组装条件
    public List<EntityA> findAll(Specification<EntityA> spec);
}
