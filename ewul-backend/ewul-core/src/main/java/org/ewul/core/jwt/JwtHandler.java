package org.ewul.core.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.ewul.model.db.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class JwtHandler {

    private static final Logger log = LoggerFactory.getLogger(JwtHandler.class);

    public static final String AUTH_ID = "auth_id";
    public static final String AUTH_ROLES = "auth_roles";
    public static final String PROPERTIES = "properties";

    public static class Builder {

        private Account account;
        private Set<String> roles;

        private Map<String, String> properties;

        private String issuer;
        private Date expiresAt;

        public Builder() {
        }

        public Builder withAccount(Account account) {
            this.account = Objects.requireNonNull(account);
            return this;
        }

        public Builder withRoles(Set<String> roles) {
            this.roles = Objects.requireNonNull(roles);
            return this;
        }

        public Builder withRole(String role) {
            Objects.requireNonNull(role);
            if (this.roles == null) {
                this.roles = new LinkedHashSet<>();
            }
            this.roles.add(role);
            return this;
        }

        public Builder withProperties(Map<String, String> properties) {
            this.properties = Objects.requireNonNull(properties);
            return this;
        }

        public Builder withProperty(String key, String value) {
            Objects.requireNonNull(key);
            Objects.requireNonNull(value);
            if (this.properties == null) {
                this.properties = new LinkedHashMap<>();
            }
            this.properties.put(key, value);
            return this;
        }

        public Builder withIssuer(String issuer) {
            this.issuer = Objects.requireNonNull(issuer);
            return this;
        }

        public Builder withExpiresAt(Date expiresAt) {
            this.expiresAt = Objects.requireNonNull(expiresAt);
            return this;
        }

        public Builder withExpiresAt(long duration, TimeUnit timeUnit) {
            if (duration < 1 || timeUnit == null) {
                throw new NullPointerException();
            }
            this.expiresAt = new Date(System.currentTimeMillis() + timeUnit.toMillis(duration));
            return this;
        }

        public String build(Algorithm algorithm) {

            if (algorithm == null) {
                throw new NullPointerException("param algorithm");
            }

            JWTCreator.Builder builder = JWT.create();

            builder.withJWTId(UUID.randomUUID().toString());

            builder.withIssuedAt(new Date());

            if (account != null) {
                String id = account.getId().toString();
                builder.withClaim(AUTH_ID, id);
            }

            if (roles != null && !roles.isEmpty()) {
                builder.withClaim(AUTH_ROLES, new ArrayList<>(roles));
            }

            if (properties != null && !properties.isEmpty()) {
                builder.withClaim(PROPERTIES, properties);
            }

            if (issuer != null) {
                builder.withIssuer(issuer);
            }

            if (expiresAt != null) {
                builder.withExpiresAt(expiresAt);
            }

            return builder.sign(algorithm);
        }

        public String build(JwtHandler jwtHandler) {

            if (jwtHandler == null) {
                throw new NullPointerException("param jwtHandler");
            }

            return build(jwtHandler.getAlgorithm());
        }

    }

    private final Algorithm algorithm;

    public JwtHandler(Algorithm algorithm) {
        this.algorithm = Objects.requireNonNull(algorithm);
    }

    protected DecodedJWT getDecodedJWT(String token) {

        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("param token");
        }

        Verification verification = JWT.require(algorithm);
        JWTVerifier verifier = verification.build();
        return verifier.verify(token);
    }

    public boolean isValid(String token) {
        try {
            return getDecodedJWT(token) != null;
        } catch (Exception ex) {
            return false;
        }
    }

    public JwtHolder decode(String token, Set<UUID> blockedIds) {
        try {
            DecodedJWT jwt = getDecodedJWT(token);
            UUID authId = UUID.fromString(jwt.getClaim(AUTH_ID).asString());

            if (blockedIds.contains(authId)) {
                throw new IllegalStateException("authId blocked");
            }

            List<String> list = jwt.getClaim(AUTH_ROLES).asList(String.class);
            if (list == null || list.isEmpty()) {
                list = Collections.emptyList();
            }
            Set<String> roles = new LinkedHashSet<>(list);

            Map<String, Object> map = jwt.getClaim(PROPERTIES).asMap();
            if (map == null || map.isEmpty()) {
                map = Collections.emptyMap();
            }
            Map<String, String> properties = Maps.transformEntries(map, (key, value) -> {
                if (key == null || value == null) {
                    throw new NullPointerException();
                }
                return value.toString();
            });

            log.info("jwt decoded: {}", authId);

            return new JwtHolder(authId, Collections.unmodifiableSet(roles), Collections.unmodifiableMap(properties));
        } catch (Exception ex) {
            throw new IllegalArgumentException("invalid jwt token", ex);
        }
    }

    public JwtHolder decode(String token) {
        return decode(token, Collections.emptySet());
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

}
