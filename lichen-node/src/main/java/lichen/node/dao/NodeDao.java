package lichen.node.dao;

import lichen.node.entities.Node;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.UUID;

/**
 * node dao object
 * @author jcai
 */
@RepositoryDefinition(domainClass = Node.class,idClass = UUID.class)
public interface NodeDao extends CrudRepository<Node,UUID>{
}
