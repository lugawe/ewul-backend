package org.ewul.model.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ActiveMQConfiguration implements Serializable {

    @Valid
    @NotNull
    private String brokerUrl;

    public ActiveMQConfiguration() {
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

}
