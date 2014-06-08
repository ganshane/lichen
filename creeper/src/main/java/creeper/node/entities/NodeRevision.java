package creeper.node.entities;

import javax.persistence.*;

/**
 * node revision
 * @author jcai
 */
@Entity
@Table(name="node_revisions")
@javax.persistence.SequenceGenerator(name="t_node_revisions_seq", sequenceName="t_node_revisions_seq" )
public class NodeRevision {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="t_node_revisions_seq")
    @Column(name="id",nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="node_id")
    private Node node;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
