package org.ewul.model.config;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class JwtConfiguration implements Serializable {

    @Valid
    @NotEmpty
    private String secret;

    public JwtConfiguration() {
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}
