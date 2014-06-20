package creeper.node.entities;

import creeper.core.CreeperCoreConstants;
import creeper.core.entities.UUIDPrimaryKeySupport;
import creeper.user.entities.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 记录每个内容实体
 * @author jcai
 */
@Entity
@Table(name="nodes")
public class Node extends UUIDPrimaryKeySupport{
    private String type;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="last_revision_id")
    private NodeRevision lastNodeRevision;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Type(type= CreeperCoreConstants.TYPE_INT_DATE)
    private Date created;
    @Type(type= CreeperCoreConstants.TYPE_INT_DATE)
    private Date changed;
    private Integer comment;



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

    public Date getChanged() {
        return changed;
    }

    public void setChanged(Date changed) {
        this.changed = changed;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }
}
