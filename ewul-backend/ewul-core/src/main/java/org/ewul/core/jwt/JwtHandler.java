package org.ewul.core.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import org.ewul.core.util.MapUtils;
import org.ewul.model.BasicUser;
import org.ewul.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class JwtHandler {

    private static final Logger log = LoggerFactory.getLogger(JwtHandler.class);

    public static final String CLAIM_AUTH_ID = "auth_id";
    public static final String CLAIM_AUTH_NAME = "auth_name";
    public static final String CLAIM_AUTH_ROLES = "auth_roles";
    public static final String CLAIM_PROPERTIES = "properties";

    public static class Builder {

        private UUID authId;
        private String authName;
        private Set<String> roles;
        private Map<String, String> properties;

        private String issuer;
        private Date expiresAt;
        private Date notBefore;

        public Builder() {
        }

        private Set<String> roles() {
            if (this.roles == null) {
                this.roles = new LinkedHashSet<>();
            }
            return this.roles;
        }

        private Map<String, String> properties() {
            if (this.properties == null) {
                this.properties = new LinkedHashMap<>();
            }
            return this.properties;
        }

        public Builder withAuthId(UUID authId) {
            this.authId = Objects.requireNonNull(authId);
            return this;
        }

        public Builder withAuthName(String authName) {
            this.authName = Objects.requireNonNull(authName);
            return this;
        }

        public Builder withUser(User user) {
            this.withAuthId(user.getId());
            this.withAuthName(user.getName());
            this.withRoles(user.getRoles(), true);
            this.withProperties(user.getProperties(), true);
            return this;
        }

        public Builder withRoles(Set<String> roles, boolean append) {
            if (append) {
                if (roles != null) {
                    this.roles().addAll(roles);
                }
            } else {
                this.roles = Objects.requireNonNull(roles);
            }
            return this;
        }

        public Builder withRoles(Set<String> roles) {
            return this.withRoles(roles, false);
        }

        public Builder withRole(String role) {
            Objects.requireNonNull(role);
            this.roles().add(role);
            return this;
        }

        public Builder withProperties(Map<String, String> properties, boolean append) {
            if (append) {
                if (properties != null) {
                    this.properties().putAll(properties);
                }
            } else {
                this.properties = Objects.requireNonNull(properties);
            }
            return this;
        }

        public Builder withProperties(Map<String, String> properties) {
            return this.withProperties(properties, false);
        }

        public Builder withProperty(String key, String value) {
            Objects.requireNonNull(key);
            Objects.requireNonNull(value);
            this.properties().put(key, value);
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
                throw new IllegalArgumentException();
            }
            Date expiresAt = new Date(System.currentTimeMillis() + timeUnit.toMillis(duration));
            return this.withExpiresAt(expiresAt);
        }

        public Builder withNotBefore(Date notBefore) {
            this.notBefore = Objects.requireNonNull(notBefore);
            return this;
        }

        public Builder withNotBefore(long duration, TimeUnit timeUnit) {
            if (duration < 1 || timeUnit == null) {
                throw new IllegalArgumentException();
            }
            Date notBefore = new Date(System.currentTimeMillis() + timeUnit.toMillis(duration));
            return this.withNotBefore(notBefore);
        }

        public String build(Algorithm algorithm) {

            if (algorithm == null) {
                throw new NullPointerException("param algorithm");
            }

            JWTCreator.Builder builder = JWT.create();

            builder.withJWTId(UUID.randomUUID().toString());

            builder.withIssuedAt(new Date());

            if (authId != null) {
                builder.withClaim(CLAIM_AUTH_ID, authId.toString());
            }

            if (authName != null) {
                builder.withClaim(CLAIM_AUTH_NAME, authName);
            }

            if (roles != null && !roles.isEmpty()) {
                builder.withClaim(CLAIM_AUTH_ROLES, new ArrayList<>(roles));
            }

            if (properties != null && !properties.isEmpty()) {
                builder.withClaim(CLAIM_PROPERTIES, new LinkedHashMap<>(properties));
            }

            if (issuer != null) {
                builder.withIssuer(issuer);
            }

            if (expiresAt != null) {
                builder.withExpiresAt(expiresAt);
            }

            if (notBefore != null) {
                builder.withNotBefore(notBefore);
            }

            return builder.sign(algorithm);
        }

        public String build(JwtHandler jwtHandler) {

            if (jwtHandler == null) {
                throw new NullPointerException("param jwtHandler");
            }

            return this.build(jwtHandler.algorithm);
        }

    }

    protected final Algorithm algorithm;

    @Inject
    public JwtHandler(Algorithm algorithm) {
        this.algorithm = Objects.requireNonNull(algorithm);
    }

    protected DecodedJWT getDecodedJWT(String token) {

        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("param token");
        }

        Verification verification = JWT.require(algorithm);
        verification.withClaimPresence(CLAIM_AUTH_ID);

        return verification.build().verify(token);
    }

    public boolean isValid(String token) {
        try {
            return getDecodedJWT(token) != null;
        } catch (Exception ignored) {
            return false;
        }
    }

    public User decode(String token, Predicate<UUID> jwtIdChecker) {
        try {
            DecodedJWT jwt = getDecodedJWT(token);
            UUID jwtId = UUID.fromString(jwt.getId());

            if (!jwtIdChecker.test(jwtId)) {
                throw new IllegalStateException(String.format("jti check failed: %s", jwtId));
            }

            UUID authId = UUID.fromString(jwt.getClaim(CLAIM_AUTH_ID).asString());
            String authName = jwt.getClaim(CLAIM_AUTH_NAME).asString();

            List<String> list = jwt.getClaim(CLAIM_AUTH_ROLES).asList(String.class);
            if (list == null || list.isEmpty()) {
                list = Collections.emptyList();
            }
            Set<String> roles = Collections.unmodifiableSet(new LinkedHashSet<>(list));

            Map<String, Object> map = jwt.getClaim(CLAIM_PROPERTIES).asMap();
            if (map == null || map.isEmpty()) {
                map = Collections.emptyMap();
            }
            map = MapUtils.sortedMap(Comparator.naturalOrder(), map);
            Map<String, String> properties = Collections.unmodifiableMap(MapUtils.toStringValueMap(map));

            log.debug("jwt decoded: {}", authId);

            return new BasicUser(authId, authName, roles, properties);
        } catch (Exception ex) {
            throw new IllegalArgumentException("invalid jwt token", ex);
        }
    }

    public User decode(String token) {
        return decode(token, Objects::nonNull);
    }

}
