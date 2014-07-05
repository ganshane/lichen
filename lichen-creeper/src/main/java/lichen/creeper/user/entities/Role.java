package lichen.creeper.user.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lichen.creeper.core.entities.UUIDPrimaryKeySupport;

/**
 * 角色
 * @author jcai
 */
@Entity
@Table(name="roles")
public class Role extends UUIDPrimaryKeySupport {
	private static final long serialVersionUID = -2752858772727853061L;
	@Column(unique = true,nullable = false)
    private String name;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "role",orphanRemoval=true)
    private Set<Permission> permissions;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
