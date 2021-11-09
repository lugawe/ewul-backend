package org.ewul.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ActiveMQConfig implements Serializable {

    @Valid
    @NotNull
    private String brokerUrl;

    @Valid
    private String brokerUserName;

    @Valid
    private String brokerPassword;

    @Valid
    @NotNull
    private ActiveMQPoolConfig activeMQPoolConfig = new ActiveMQPoolConfig();

    public ActiveMQConfig() {
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public String getBrokerUserName() {
        return brokerUserName;
    }

    public void setBrokerUserName(String brokerUserName) {
        this.brokerUserName = brokerUserName;
    }

    public String getBrokerPassword() {
        return brokerPassword;
    }

    public void setBrokerPassword(String brokerPassword) {
        this.brokerPassword = brokerPassword;
    }

    @JsonProperty("pool")
    public ActiveMQPoolConfig getActiveMQPoolConfig() {
        return activeMQPoolConfig;
    }

    @JsonProperty("pool")
    public void setActiveMQPoolConfig(ActiveMQPoolConfig activeMQPoolConfig) {
        this.activeMQPoolConfig = activeMQPoolConfig;
    }

}
