package creeper.node.entities;

import javax.persistence.*;

/**
 * comment
 * @author jcai
 */
@Entity
@Table(name="comments")
@javax.persistence.SequenceGenerator(name="t_comments_seq", sequenceName="t_comments_seq" )
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="t_comments_seq")
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
