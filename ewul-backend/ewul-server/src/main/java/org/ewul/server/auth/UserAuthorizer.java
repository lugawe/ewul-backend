package org.ewul.server.auth;

import io.dropwizard.auth.Authorizer;
import org.ewul.model.User;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Singleton
public class UserAuthorizer implements Authorizer<User> {

    @Inject
    public UserAuthorizer() {
    }

    protected Set<String> emptyIfNull(Set<String> set) {
        return set != null ? set : Collections.emptySet();
    }

    @Override
    public boolean authorize(User user, String role, @Nullable ContainerRequestContext context) {
        if (user == null || role == null) {
            return false;
        }
        return emptyIfNull(user.getRoles()).stream().filter(Objects::nonNull).anyMatch(r -> r.equals(role));
    }

    @Override
    public boolean authorize(User user, String role) {
        return authorize(user, role, null);
    }

}
