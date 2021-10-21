package org.ewul.server.auth;

import io.dropwizard.auth.Authorizer;
import org.ewul.core.util.CollectionUtils;
import org.ewul.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

public class UserAuthorizer implements Authorizer<User> {

    private static final Logger log = LoggerFactory.getLogger(UserAuthorizer.class);

    @Inject
    public UserAuthorizer() {
    }

    protected Predicate<ContainerRequestContext> requestContextChecker() {
        return ctx -> true;
    }

    @Override
    public boolean authorize(User user, String role, @Nullable ContainerRequestContext context) {
        if (user == null || role == null || (context != null && requestContextChecker().test(context))) {
            return false;
        }
        Collection<String> roles = CollectionUtils.emptyIfNull(user.getRoles());
        boolean success = roles.stream().filter(Objects::nonNull).anyMatch(r -> r.equals(role));
        if (success) {
            log.info("user {} has successfully requested role {}", user, role);
        }
        return success;
    }

    @Override
    public boolean authorize(User user, String role) {
        return authorize(user, role, null);
    }

}
