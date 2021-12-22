package org.ewul.core.modules.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.ewul.core.util.MapUtils;
import org.ewul.model.BasicUser;
import org.ewul.model.User;
import org.ewul.model.config.CoreConfiguration;
import org.ewul.model.config.JwtConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Singleton
public class JwtTokenHandler implements TokenHandler {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenHandler.class);

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
                throw new NullPointerException("algorithm");
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

        public String build(JwtTokenHandler jwtTokenHandler) {

            if (jwtTokenHandler == null) {
                throw new NullPointerException("jwtTokenHandler");
            }

            return this.build(jwtTokenHandler.algorithm);
        }

    }

    protected final JwtConfiguration jwtConfiguration;
    protected final Algorithm algorithm;

    public JwtTokenHandler(JwtConfiguration jwtConfiguration, Algorithm algorithm) {
        this.jwtConfiguration = Objects.requireNonNull(jwtConfiguration);
        this.algorithm = Objects.requireNonNull(algorithm);
    }

    public JwtTokenHandler(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = Objects.requireNonNull(jwtConfiguration);
        this.algorithm = Objects.requireNonNull(buildAlgorithm());
    }

    @Inject
    public JwtTokenHandler(CoreConfiguration coreConfiguration) {
        this(coreConfiguration.getJwtConfiguration());
    }

    protected Algorithm buildAlgorithm() {
        String secret = jwtConfiguration.getSecret();
        if (secret == null) {
            secret = UUID.randomUUID().toString();
        }
        return Algorithm.HMAC512(secret);
    }

    @Override
    public String generateAccessToken(User user) {

        if (user == null) {
            throw new NullPointerException("user");
        }

        String issuer = jwtConfiguration.getIssuer();
        Duration lifetime = jwtConfiguration.getLifetime();

        return new Builder()
                .withUser(user)
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + lifetime.toMillis()))
                .build(algorithm);
    }

    @Override
    public Optional<User> decodeAccessToken(String token) {
        try {
            if (token == null || token.isEmpty()) {
                throw new IllegalArgumentException("token");
            }

            Verification verification = JWT.require(algorithm);
            verification.withClaimPresence(CLAIM_AUTH_ID);

            DecodedJWT jwt = verification.build().verify(token);

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

            return Optional.of(new BasicUser(authId, authName, roles, properties));
        } catch (Exception e) {
            log.debug("invalid jwt", e);
            return Optional.empty();
        }
    }

}
