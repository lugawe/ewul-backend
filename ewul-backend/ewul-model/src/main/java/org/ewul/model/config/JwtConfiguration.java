package org.ewul.model.config;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private Duration refreshTokenLifetime = Duration.ofDays(14);

    @Valid
    @NotNull
    private Duration accessTokenLifetime = Duration.ofHours(4);

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

    public Duration getRefreshTokenLifetime() {
        return refreshTokenLifetime;
    }

    public void setRefreshTokenLifetime(Duration refreshTokenLifetime) {
        this.refreshTokenLifetime = refreshTokenLifetime;
    }

    public Duration getAccessTokenLifetime() {
        return accessTokenLifetime;
    }

    public void setAccessTokenLifetime(Duration accessTokenLifetime) {
        this.accessTokenLifetime = accessTokenLifetime;
    }

}
