package lichen.creeper.test.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import lichen.creeper.test.entities.EntityB;

/**
 * @author jcai
 */
@RepositoryDefinition(domainClass = EntityB.class,idClass = String.class)
public interface EntityBDao extends CrudRepository<EntityB, String> {
    public EntityB findByName(String name);
}
