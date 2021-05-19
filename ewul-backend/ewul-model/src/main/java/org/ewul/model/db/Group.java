package org.ewul.model.db;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "auth_group")
public class Group implements Iterable<Role>, Model {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "id", updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "auth_group_role",
            joinColumns = @JoinColumn(name = "auth_group", foreignKey = @ForeignKey(name = "fk_auth_group_role_group")),
            inverseJoinColumns = @JoinColumn(name = "auth_role", foreignKey = @ForeignKey(name = "fk_auth_group_role_role"))
    )
    private List<Role> roles;

    public Group() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id) && Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public Iterator<Role> iterator() {
        return roles != null ? roles.iterator() : Collections.emptyIterator();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
