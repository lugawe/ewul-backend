package org.ewul.model;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class BasicUser implements User {

    private UUID id;
    private String name;
    private Set<String> roles;
    private Map<String, String> properties;

    public BasicUser() {
    }

    public BasicUser(UUID id, String name, Set<String> roles, Map<String, String> properties) {
        this.id = id;
        this.name = name;
        this.roles = roles;
        this.properties = properties;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BasicUser)) return false;
        BasicUser user = (BasicUser) obj;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return String.format("BasicUser %s (%s)", name, id);
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

}
