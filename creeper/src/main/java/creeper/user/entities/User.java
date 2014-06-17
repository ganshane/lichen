package creeper.user.entities;

import creeper.core.CreeperCoreConstants;
import creeper.core.entities.UUIDPrimaryKeySupport;

import java.io.Serializable;
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
	
	private String name;
	private String pass;
    private String mail;
    private Integer created;
    private Integer access;
    private Integer login;
    @Column(insertable = false)
    private Integer status;
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

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
