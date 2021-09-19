package org.ewul.server.auth;

import io.dropwizard.auth.Authorizer;
import org.ewul.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.container.ContainerRequestContext;

@Singleton
public class UserAuthorizer implements Authorizer<User> {

    private static final Logger log = LoggerFactory.getLogger(UserAuthorizer.class);

    @Inject
    public UserAuthorizer() {
    }

    @Override
    public boolean authorize(User user, String role, @Nullable ContainerRequestContext context) {
        return false;
    }

    @Override
    public boolean authorize(User user, String role) {
        return authorize(user, role, null);
    }

}
