package lichen.node.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lichen.creeper.core.CreeperCoreConstants;
import lichen.creeper.core.entities.UUIDPrimaryKeySupport;
import lichen.creeper.user.entities.User;

/**
 * node revision
 * @author jcai
 */
@Entity
@Table(name="node_revisions")
public class NodeRevision extends UUIDPrimaryKeySupport{
	private static final long serialVersionUID = 3450278342553679681L;
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
