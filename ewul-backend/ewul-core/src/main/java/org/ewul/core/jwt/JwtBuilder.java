package org.ewul.core.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.ewul.core.util.MapperUtils;
import org.ewul.model.User;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class JwtBuilder {

    public static final String CLAIM_TOKEN_TYPE = "token_type";

    public static final String CLAIM_AUTH_ID = "auth_id";
    public static final String CLAIM_USER = "user";

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

    protected JWTCreator.Builder builder(TokenType tokenType) {
        JWTCreator.Builder builder = JWT.create();
        builder.withJWTId(UUID.randomUUID().toString());
        builder.withIssuedAt(new Date());
        builder.withClaim(CLAIM_TOKEN_TYPE, tokenType.toString());
        if (issuer != null) {
            builder.withIssuer(issuer);
        }
        if (expiresAt != null) {
            builder.withExpiresAt(expiresAt);
        }
        return builder;
    }

    public String buildRefreshToken(User user) {

        if (user == null) {
            throw new NullPointerException("user");
        }

        JWTCreator.Builder builder = builder(TokenType.REFRESH);

        builder.withClaim(CLAIM_AUTH_ID, user.getId().toString());

        return builder.sign(algorithm);
    }

    public String buildAccessToken(User user) {

        if (user == null) {
            throw new NullPointerException("user");
        }

        JWTCreator.Builder builder = builder(TokenType.ACCESS);

        builder.withClaim(CLAIM_USER, MapperUtils.toMap(user));

        return builder.sign(algorithm);
    }

}
