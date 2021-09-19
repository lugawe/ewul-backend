package org.ewul.server.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.ewul.core.jwt.JwtHandler;
import org.ewul.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class UserAuthenticator implements Authenticator<String, User> {

    private static final Logger log = LoggerFactory.getLogger(UserAuthenticator.class);

    protected final JwtHandler jwtHandler;

    @Inject
    public UserAuthenticator(JwtHandler jwtHandler) {
        this.jwtHandler = Objects.requireNonNull(jwtHandler);
    }

    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        try {
            User user = jwtHandler.decode(token);
            if (user == null || user.getId() == null) {
                throw new IllegalStateException("invalid jwt token");
            }
            log.info("user has successfully authenticated: {}", user);
            return Optional.of(user);
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    public JwtHandler getJwtHandler() {
        return jwtHandler;
    }

}
