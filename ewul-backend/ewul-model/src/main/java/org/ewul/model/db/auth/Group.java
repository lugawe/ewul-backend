package org.ewul.model.db.auth;

import org.ewul.model.db.DbModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "auth_group")
public class Group implements Iterable<Role>, DbModel {

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
            joinColumns = @JoinColumn(name = "auth_group", foreignKey = @ForeignKey(name = "fk_auth__group_role_group")),
            inverseJoinColumns = @JoinColumn(name = "auth_role", foreignKey = @ForeignKey(name = "fk_auth__group_role_role"))
    )
    private Set<Role> roles;

    public Group() {
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Group)) return false;
        Group group = (Group) other;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
