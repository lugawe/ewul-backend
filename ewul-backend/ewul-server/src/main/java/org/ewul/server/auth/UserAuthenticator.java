package org.ewul.server.auth;

import io.dropwizard.auth.Authenticator;
import org.ewul.core.modules.auth.AccessHandler;
import org.ewul.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

public class UserAuthenticator implements Authenticator<String, User> {

    private static final Logger log = LoggerFactory.getLogger(UserAuthenticator.class);

    private final AccessHandler accessHandler;

    @Inject
    public UserAuthenticator(AccessHandler accessHandler) {
        this.accessHandler = Objects.requireNonNull(accessHandler);
    }

    @Override
    public Optional<User> authenticate(String token) {
        Optional<User> result = accessHandler.decodeAccessToken(token);
        result.ifPresent((user) -> log.info("user {} has successfully authenticated", user));
        return result;
    }

}
