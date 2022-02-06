package org.ewul.server.auth;

import io.dropwizard.auth.Authenticator;
import org.ewul.core.modules.auth.AuthManager;
import org.ewul.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

public class UserAuthenticator implements Authenticator<String, User> {

    private static final Logger log = LoggerFactory.getLogger(UserAuthenticator.class);

    private final AuthManager authManager;

    @Inject
    public UserAuthenticator(AuthManager authManager) {
        this.authManager = Objects.requireNonNull(authManager);
    }

    @Override
    public Optional<User> authenticate(String accessToken) {
        if (log.isDebugEnabled()) {
            log.debug("authenticate: accessToken={}", accessToken);
        }
        Optional<User> result = authManager.decodeAccessToken(accessToken);
        result.ifPresent((user) -> log.info("user {} has been successfully authenticated", user));
        return result;
    }

}
