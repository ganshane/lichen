package lichen.node.dao;

import lichen.node.entities.Node;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * node dao object
 * @author jcai
 */
@RepositoryDefinition(domainClass = Node.class,idClass = String.class)
public interface NodeDao extends CrudRepository<Node,String>{
}
