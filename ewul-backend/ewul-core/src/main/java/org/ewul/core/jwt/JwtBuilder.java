package org.ewul.core.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.ewul.core.modules.auth.TokenType;
import org.ewul.core.util.MapperUtils;
import org.ewul.model.User;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class JwtBuilder {

    protected final Algorithm algorithm;

    private String issuer;
    private Date expiresAt;

    public JwtBuilder(Algorithm algorithm) {
        this.algorithm = Objects.requireNonNull(algorithm, "algorithm");
    }

    public JwtBuilder withIssuer(String issuer) {
        this.issuer = Objects.requireNonNull(issuer);
        return this;
    }

    public JwtBuilder withExpiresAt(Date expiresAt) {
        this.expiresAt = Objects.requireNonNull(expiresAt);
        return this;
    }

    public JwtBuilder withExpiresAt(long duration, TimeUnit timeUnit) {
        if (duration < 1 || timeUnit == null) {
            throw new IllegalArgumentException();
        }
        Date expiresAt = new Date(System.currentTimeMillis() + timeUnit.toMillis(duration));
        return this.withExpiresAt(expiresAt);
    }

    public JwtBuilder withExpiresAt(Duration duration) {
        if (duration == null) {
            throw new IllegalArgumentException();
        }
        Date expiresAt = new Date(System.currentTimeMillis() + duration.toMillis());
        return this.withExpiresAt(expiresAt);
    }

    protected JWTCreator.Builder builder(TokenType tokenType) {
        JWTCreator.Builder builder = JWT.create();
        builder.withJWTId(UUID.randomUUID().toString());
        builder.withIssuedAt(new Date());
        builder.withClaim(Jwt.CLAIM_TOKEN_TYPE, tokenType.toString());
        if (issuer != null) {
            builder.withIssuer(issuer);
        }
        if (expiresAt != null) {
            builder.withExpiresAt(expiresAt);
        }
        return builder;
    }

    public String buildRefreshToken(UUID id) {

        if (id == null) {
            throw new NullPointerException("id");
        }

        JWTCreator.Builder builder = builder(TokenType.REFRESH);

        builder.withClaim(Jwt.CLAIM_AUTH_ID, id.toString());

        return builder.sign(algorithm);
    }

    public String buildAccessToken(User user) {

        if (user == null) {
            throw new NullPointerException("user");
        }

        JWTCreator.Builder builder = builder(TokenType.ACCESS);

        builder.withClaim(Jwt.CLAIM_USER, MapperUtils.toMap(user));

        return builder.sign(algorithm);
    }

}
