package org.ewul.server.auth;

import io.dropwizard.auth.AuthFilter;
import org.ewul.model.User;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.SecurityContext;
import java.util.Map;
import java.util.Optional;

@Priority(Priorities.AUTHENTICATION)
public class JwtCookieAuthFilter extends AuthFilter<String, User> {

    public static final String AUTH_COOKIE_NAME = "auth_jwt";

    public static class Builder extends AuthFilterBuilder<String, User, JwtCookieAuthFilter> {

        public Builder() {
        }

        @Override
        public JwtCookieAuthFilter newInstance() {
            return new JwtCookieAuthFilter();
        }

    }

    @Inject
    public JwtCookieAuthFilter() {
    }

    @Override
    public void filter(ContainerRequestContext context) {

        try {

            Map<String, Cookie> cookies = context.getCookies();
            if (cookies == null || cookies.size() < 1) {
                throw new IllegalStateException("no cookies provided");
            }

            Optional<Cookie> cookie = Optional.ofNullable(cookies.get(AUTH_COOKIE_NAME));
            if (!cookie.isPresent()) {
                throw new IllegalStateException(String.format("no %s cookie provided", AUTH_COOKIE_NAME));
            }

            if (!authenticate(context, cookie.get().getValue(), SecurityContext.BASIC_AUTH)) {
                throw new IllegalStateException("invalid jwt token");
            }

        } catch (Exception ex) {
            throw new WebApplicationException(ex, unauthorizedHandler.buildResponse(prefix, realm));
        }

    }

}
