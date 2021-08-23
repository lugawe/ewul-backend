package org.ewul.model.db;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "membership")
public class Membership implements Iterable<Role>, DbModel {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "id", updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "membership_role",
            joinColumns = @JoinColumn(name = "membership", foreignKey = @ForeignKey(name = "fk_membership_role_membership")),
            inverseJoinColumns = @JoinColumn(name = "role", foreignKey = @ForeignKey(name = "fk_membership_role_role"))
    )
    private List<Role> roles;

    public Membership() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Membership)) return false;
        Membership membership = (Membership) o;
        return Objects.equals(id, membership.id) && Objects.equals(name, membership.name);
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
