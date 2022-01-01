package org.ewul.server.auth;

import io.dropwizard.auth.AuthFilter;
import org.ewul.model.User;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;

@Priority(Priorities.AUTHENTICATION)
public class CookieAuthFilter extends AuthFilter<String, User> {

    public static class Builder extends AuthFilterBuilder<String, User, CookieAuthFilter> {

        @Inject
        public Builder() {
        }

        @Override
        public CookieAuthFilter newInstance() {
            return new CookieAuthFilter();
        }

    }

    @Inject
    public CookieAuthFilter() {
    }

    @Override
    public void filter(ContainerRequestContext context) {

        try {

            String value = AuthCookies.extractAccessToken(context.getCookies());

            if (value == null || !authenticate(context, value, SecurityContext.BASIC_AUTH)) {
                throw new IllegalStateException("invalid access token");
            }

        } catch (Exception ex) {
            throw new WebApplicationException(ex, unauthorizedHandler.buildResponse(prefix, realm));
        }

    }

}
