package lichen.node.dao;

import lichen.node.entities.NodeRevision;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author jcai
 */
@RepositoryDefinition(domainClass = NodeRevision.class,idClass = Integer.class)
public interface NodeRevisionDao extends CrudRepository<NodeRevision,Integer>{
}
