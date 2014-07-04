package creeper.node.dao;

import creeper.node.entities.NodeRevision;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author jcai
 */
@RepositoryDefinition(domainClass = NodeRevision.class,idClass = Integer.class)
public interface NodeRevisionDao extends CrudRepository<NodeRevision,Integer>{
}
