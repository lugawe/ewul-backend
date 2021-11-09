package org.ewul.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CoreConfiguration implements Serializable {

    private static final CoreConfiguration EMPTY = new CoreConfiguration();

    @Valid
    @NotNull
    private ActiveMQConfig activeMQConfig = new ActiveMQConfig();

    @Valid
    @NotNull
    private JwtConfiguration jwtConfiguration = new JwtConfiguration();

    public CoreConfiguration() {
    }

    @JsonProperty("activemq")
    public ActiveMQConfig getActiveMQConfig() {
        return activeMQConfig;
    }

    @JsonProperty("activemq")
    public void setActiveMQConfig(ActiveMQConfig activeMQConfig) {
        this.activeMQConfig = activeMQConfig;
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
