package creeper.node.entities;

import creeper.core.CreeperCoreConstants;
import creeper.core.entities.UUIDPrimaryKeySupport;
import creeper.user.entities.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * node revision
 * @author jcai
 */
@Entity
@Table(name="node_revisions")
public class NodeRevision extends UUIDPrimaryKeySupport{
    @ManyToOne
    @JoinColumn(name="node_id")
    private Node node;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    private String title;
    private String body;
    private String log;
    @Type(type= CreeperCoreConstants.TYPE_INT_DATE)
    private Date created;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
