package org.ewul.server.auth;

import io.dropwizard.auth.AuthFilter;
import org.ewul.model.User;
import org.ewul.server.util.CookieBuilder;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.SecurityContext;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Priority(Priorities.AUTHENTICATION)
public class JwtCookieAuthFilter extends AuthFilter<String, User> {

    public static final String AUTH_COOKIE_NAME = "auth_jwt";

    public static class Builder extends AuthFilterBuilder<String, User, JwtCookieAuthFilter> {

        @Inject
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

    public static NewCookie createDefaultCookie(String jwt, Duration lifetime) {
        Objects.requireNonNull(jwt);
        Objects.requireNonNull(lifetime);
        int maxAge = (int) (lifetime.toMillis() / 1000);
        return new CookieBuilder()
                .withName(AUTH_COOKIE_NAME)
                .withValue(jwt)
                .withMaxAge(maxAge)
                .withHttpOnly(true)
                .withPath("/api/")
                .buildNewCookie();
    }

}
