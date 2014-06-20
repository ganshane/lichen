package creeper.user.entities;

import creeper.core.CreeperCoreConstants;
import creeper.core.entities.UUIDPrimaryKeySupport;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

/**
 * 
 * @author shen
 *
 */
@Entity
@Table(name="users")
public class User extends UUIDPrimaryKeySupport {
	private static final long serialVersionUID = 9166431956890572840L;
    public static enum UserStatus{
       Activated,Locked
    }
	
	private String name;
	private String pass;
    private String mail;
    @Type(type=CreeperCoreConstants.TYPE_INT_DATE)
    private Date created;
    @Type(type=CreeperCoreConstants.TYPE_INT_DATE)
    private Date access;
    @Type(type=CreeperCoreConstants.TYPE_INT_DATE)
    private Date login;
    @Column(insertable = false)
    @Enumerated
    private UserStatus status;
    @ManyToMany
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id")
    )
    private Set<Role> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getAccess() {
        return access;
    }

    public void setAccess(Date access) {
        this.access = access;
    }

    public Date getLogin() {
        return login;
    }

    public void setLogin(Date login) {
        this.login = login;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
