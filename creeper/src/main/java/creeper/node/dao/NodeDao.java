package creeper.node.dao;

import creeper.node.entities.Node;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * node dao object
 * @author jcai
 */
@RepositoryDefinition(domainClass = Node.class,idClass = Integer.class)
public interface NodeDao extends CrudRepository<Node,Integer>{
}
