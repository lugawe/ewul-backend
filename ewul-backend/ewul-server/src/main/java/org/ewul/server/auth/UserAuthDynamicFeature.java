package org.ewul.server.auth;

import io.dropwizard.auth.AuthDynamicFeature;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class UserAuthDynamicFeature extends AuthDynamicFeature {

    @Inject
    public UserAuthDynamicFeature(UserAuthenticator authenticator, UserAuthorizer authorizer) {
        super(buildFilter(authenticator, authorizer));
    }

    public static ContainerRequestFilter buildFilter(UserAuthenticator authenticator, UserAuthorizer authorizer) {
        if (authenticator == null) {
            throw new NullPointerException("authenticator");
        }
        if (authorizer == null) {
            throw new NullPointerException("authorizer");
        }
        return new CookieAuthFilter.Builder()
                .setAuthenticator(authenticator)
                .setAuthorizer(authorizer)
                .buildAuthFilter();
    }

}
