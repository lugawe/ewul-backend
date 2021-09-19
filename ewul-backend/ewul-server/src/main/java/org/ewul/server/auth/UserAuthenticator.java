package org.ewul.server.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.ewul.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Optional;

public class UserAuthenticator implements Authenticator<String, User> {

    private static final Logger log = LoggerFactory.getLogger(UserAuthenticator.class);

    @Inject
    public UserAuthenticator() {
    }

    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        return Optional.empty();
    }

}
