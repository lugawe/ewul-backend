package org.ewul.model;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class BasicUser implements User {

    private UUID id;
    private String name;
    private Collection<String> roles;
    private Map<String, String> properties;

    public BasicUser() {
    }

    public BasicUser(UUID id, String name, Collection<String> roles, Map<String, String> properties) {
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
    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
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