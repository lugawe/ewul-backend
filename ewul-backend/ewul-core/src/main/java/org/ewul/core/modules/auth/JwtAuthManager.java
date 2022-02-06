package org.ewul.core.modules.auth;

import com.auth0.jwt.algorithms.Algorithm;
import org.ewul.core.jwt.JwtBuilder;
import org.ewul.core.jwt.JwtDecoder;
import org.ewul.model.User;
import org.ewul.model.config.CoreConfiguration;
import org.ewul.model.config.JwtConfiguration;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class JwtAuthManager extends AbstractAuthManager {

    private final JwtConfiguration jwtConfiguration;
    private final Algorithm algorithm;

    public JwtAuthManager(JwtConfiguration jwtConfiguration, Algorithm algorithm) {
        this.jwtConfiguration = Objects.requireNonNull(jwtConfiguration);
        this.algorithm = Objects.requireNonNull(algorithm);
    }

    public JwtAuthManager(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = Objects.requireNonNull(jwtConfiguration);
        this.algorithm = buildAlgorithm();
    }

    @Inject
    public JwtAuthManager(CoreConfiguration configuration) {
        this(configuration.getJwtConfiguration());
    }

    private Algorithm buildAlgorithm() {
        String secret = jwtConfiguration.getSecret();
        if (secret == null) {
            secret = UUID.randomUUID().toString();
        }
        return Algorithm.HMAC512(secret);
    }

    @Override
    public String generateRefreshToken(UUID id) {
        return new JwtBuilder(algorithm)
                .withIssuer(jwtConfiguration.getIssuer())
                .withExpiresAt(jwtConfiguration.getRefreshTokenLifetime())
                .buildRefreshToken(id);
    }

    @Override
    public String generateAccessToken(User user) {
        return new JwtBuilder(algorithm)
                .withIssuer(jwtConfiguration.getIssuer())
                .withExpiresAt(jwtConfiguration.getAccessTokenLifetime())
                .buildAccessToken(user);
    }

    @Override
    public Optional<UUID> decodeRefreshToken(String refreshToken) {
        return new JwtDecoder(algorithm).decodeRefreshToken(refreshToken);
    }

    @Override
    public Optional<User> decodeAccessToken(String accessToken) {
        return new JwtDecoder(algorithm).decodeAccessToken(accessToken);
    }

}
