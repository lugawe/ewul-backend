package org.ewul.model.config;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.Duration;
import java.util.UUID;

public class JwtConfiguration implements Serializable {

    @Valid
    @NotEmpty
    private String secret = UUID.randomUUID().toString();

    @Valid
    @NotEmpty
    private String issuer = "ewul";

    @Valid
    @NotEmpty
    private Duration lifetime = Duration.ofDays(14);

    public JwtConfiguration() {
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Duration getLifetime() {
        return lifetime;
    }

    public void setLifetime(Duration lifetime) {
        this.lifetime = lifetime;
    }

}
