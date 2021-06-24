package org.ewul.core.jwt;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class JwtHolder implements Serializable {

    private UUID authId;
    private Set<String> roles;

    private Map<String, String> properties;

    public JwtHolder() {
    }

    public JwtHolder(UUID authId, Set<String> roles, Map<String, String> properties) {
        this.authId = authId;
        this.roles = roles;
        this.properties = properties;
    }

    public UUID getAuthId() {
        return authId;
    }

    public void setAuthId(UUID authId) {
        this.authId = authId;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

}
