package creeper.node.entities;

import creeper.core.CreeperCoreConstants;
import creeper.core.entities.UUIDPrimaryKeySupport;
import creeper.user.entities.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * comment
 * @author jcai
 */
@Entity
@Table(name="comments")
public class Comment extends UUIDPrimaryKeySupport{
    private String title;
    private String body;
    private String thread;//用来展示树形结构
    @ManyToOne
    @JoinColumn(name="node_id")
    private Node node;//关联的主题
    @ManyToOne
    @JoinColumn(name="parent_id")
    private Comment parent;//关联的父注释
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Type(type= CreeperCoreConstants.TYPE_INT_DATE)
    private Date created;

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

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
