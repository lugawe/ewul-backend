package org.ewul.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CoreConfiguration implements Serializable {

    private static final CoreConfiguration EMPTY = new CoreConfiguration();

    private JwtConfiguration jwtConfiguration = new JwtConfiguration();

    public CoreConfiguration() {
    }

    @JsonProperty("jwt")
    public JwtConfiguration getJwtConfiguration() {
        return jwtConfiguration;
    }

    @JsonProperty("jwt")
    public void setJwtConfiguration(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public static CoreConfiguration empty() {
        return EMPTY;
    }

}
