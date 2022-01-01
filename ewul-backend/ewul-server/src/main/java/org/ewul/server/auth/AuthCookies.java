package org.ewul.server.auth;

import org.ewul.server.Constants;
import org.ewul.server.util.CookieBuilder;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class AuthCookies {

    public static final String AUTH_ACCESS_TOKEN = "auth_access_token";
    //public static final String AUTH_REFRESH_TOKEN = "auth_refresh_token";

    private AuthCookies() {
    }

    public static String extractAccessToken(Map<String, Cookie> cookies) throws IllegalArgumentException {

        if (cookies == null || cookies.size() < 1) {
            throw new IllegalArgumentException("no cookies provided");
        }

        Optional<Cookie> cookie = Optional.ofNullable(cookies.get(AUTH_ACCESS_TOKEN));
        if (!cookie.isPresent()) {
            throw new IllegalArgumentException(String.format("no %s cookie provided", AUTH_ACCESS_TOKEN));
        }

        return cookie.get().getValue();
    }

    public static NewCookie createAccessTokenCookie(String token, Duration lifetime) {
        Objects.requireNonNull(token);
        Objects.requireNonNull(lifetime);
        int maxAge = (int) (lifetime.toMillis() / 1000);
        return new CookieBuilder()
                .withName(AUTH_ACCESS_TOKEN)
                .withValue(token)
                .withMaxAge(maxAge)
                .withHttpOnly(true)
                .withPath(Constants.API_PATH)
                .buildNewCookie();
    }

}
