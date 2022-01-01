package org.ewul.server.auth;

import org.ewul.server.Constants;
import org.ewul.server.util.CookieBuilder;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class JwtCookie {

    public static final String AUTH_COOKIE_NAME = "auth_jwt";

    private JwtCookie() {
    }

    public static String extractCookieValue(Map<String, Cookie> cookies) throws IllegalArgumentException {

        if (cookies == null || cookies.size() < 1) {
            throw new IllegalArgumentException("no cookies provided");
        }

        Optional<Cookie> cookie = Optional.ofNullable(cookies.get(AUTH_COOKIE_NAME));
        if (!cookie.isPresent()) {
            throw new IllegalArgumentException(String.format("no %s cookie provided", AUTH_COOKIE_NAME));
        }

        return cookie.get().getValue();
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
                .withPath(Constants.API_PATH)
                .buildNewCookie();
    }

}
