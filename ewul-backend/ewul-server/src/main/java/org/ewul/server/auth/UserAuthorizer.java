package org.ewul.server.auth;

import io.dropwizard.auth.Authorizer;
import org.ewul.core.modules.auth.AuthManager;
import org.ewul.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.Objects;

public class UserAuthorizer implements Authorizer<User> {

    private static final Logger log = LoggerFactory.getLogger(UserAuthorizer.class);

    private final AuthManager authManager;

    @Inject
    public UserAuthorizer(AuthManager authManager) {
        this.authManager = Objects.requireNonNull(authManager);
    }

    @Override
    public boolean authorize(User user, String role, @Nullable ContainerRequestContext requestContext) {
        if (log.isDebugEnabled()) {
            log.debug("authorize: user={}, role={}, requestContext={}", user, role, requestContext);
        }
        boolean success = authManager.hasAccess(user, role);
        if (success) {
            log.info("user {} has been successfully requested role {}", user, role);
        }
        return success;
    }

    @Override
    public boolean authorize(User user, String role) {
        return authorize(user, role, null);
    }

}
