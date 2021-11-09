package org.ewul.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ActiveMQConfiguration implements Serializable {

    @Valid
    @NotNull
    private String brokerUrl;

    @Valid
    private String brokerUserName;

    @Valid
    private String brokerPassword;

    @Valid
    @NotNull
    private ActiveMQPoolConfiguration activeMQPoolConfiguration = new ActiveMQPoolConfiguration();

    public ActiveMQConfiguration() {
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
    public ActiveMQPoolConfiguration getActiveMQPoolConfiguration() {
        return activeMQPoolConfiguration;
    }

    @JsonProperty("pool")
    public void setActiveMQPoolConfiguration(ActiveMQPoolConfiguration activeMQPoolConfiguration) {
        this.activeMQPoolConfiguration = activeMQPoolConfiguration;
    }

}
