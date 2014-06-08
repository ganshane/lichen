package creeper.node.entities;

import javax.persistence.*;
import java.util.List;

/**
 * 记录每个内容实体
 * @author jcai
 */
@Entity
@Table(name="nodes")
@javax.persistence.SequenceGenerator(name="t_nodes_seq", sequenceName="t_nodes_seq" )
public class Node {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="t_nodes_seq")
    @Column(name="id",nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="last_revision_id")
    private NodeRevision lastNodeRevision;

    private String type;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public NodeRevision getLastNodeRevision() {
        return lastNodeRevision;
    }

    public void setLastNodeRevision(NodeRevision lastNodeRevision) {
        this.lastNodeRevision = lastNodeRevision;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
