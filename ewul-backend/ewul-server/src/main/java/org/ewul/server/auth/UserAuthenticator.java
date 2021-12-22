package org.ewul.server.auth;

import io.dropwizard.auth.Authenticator;
import org.ewul.core.modules.auth.TokenHandler;
import org.ewul.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

public class UserAuthenticator implements Authenticator<String, User> {

    private static final Logger log = LoggerFactory.getLogger(UserAuthenticator.class);

    protected final TokenHandler tokenHandler;

    @Inject
    public UserAuthenticator(TokenHandler tokenHandler) {
        this.tokenHandler = Objects.requireNonNull(tokenHandler);
    }

    @Override
    public Optional<User> authenticate(String token) {
        Optional<User> result = tokenHandler.decodeAccessToken(token);
        result.ifPresent((user) -> log.info("user {} has successfully authenticated", user));
        return result;
    }

}
