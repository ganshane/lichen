package creeper.user.entities;

import creeper.core.entities.UUIDPrimaryKeySupport;

import javax.persistence.*;
import java.util.Set;

/**
 * 角色
 * @author jcai
 */
@Entity
@Table(name="roles")
public class Role extends UUIDPrimaryKeySupport {
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
