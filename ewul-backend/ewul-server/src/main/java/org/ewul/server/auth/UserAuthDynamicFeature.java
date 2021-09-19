package org.ewul.server.auth;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.setup.Environment;
import org.ewul.model.User;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class UserAuthDynamicFeature extends AuthDynamicFeature {

    @Inject
    public UserAuthDynamicFeature(Environment environment,
                                  UserAuthenticator authenticator,
                                  UserAuthorizer authorizer) {

        super(buildFilter(authenticator, authorizer));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    public static ContainerRequestFilter buildFilter(UserAuthenticator authenticator, UserAuthorizer authorizer) {
        if (authenticator == null) {
            throw new NullPointerException("authenticator");
        }
        if (authorizer == null) {
            throw new NullPointerException("authorizer");
        }
        return new JwtCookieAuthFilter.Builder()
                .setAuthenticator(authenticator)
                .setAuthorizer(authorizer)
                .buildAuthFilter();
    }

}
