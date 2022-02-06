package org.ewul.server.auth;

import org.ewul.core.modules.auth.TokenType;
import org.ewul.server.Constants;
import org.ewul.server.util.CookieBuilder;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import java.util.*;

public final class AuthCookies {

    public static final String AUTH_REFRESH_TOKEN = "auth_refresh_token";
    public static final String AUTH_ACCESS_TOKEN = "auth_access_token";

    private AuthCookies() {
    }

    public static Map<TokenType, String> extractTokens(Map<String, Cookie> cookies) {
        try {

            if (cookies == null || cookies.size() < 1) {
                throw new IllegalArgumentException("no cookies provided");
            }

            Map<TokenType, String> result = new LinkedHashMap<>();

            Optional<Cookie> refreshTokenCookie = Optional.ofNullable(cookies.get(AUTH_REFRESH_TOKEN));
            refreshTokenCookie.ifPresent(cookie -> result.put(TokenType.REFRESH, cookie.getValue()));

            Optional<Cookie> accessTokenCookie = Optional.ofNullable(cookies.get(AUTH_ACCESS_TOKEN));
            accessTokenCookie.ifPresent(cookie -> result.put(TokenType.ACCESS, cookie.getValue()));

            return Collections.unmodifiableMap(result);
        } catch (Exception e) {
            // no valid cookie
            return Collections.emptyMap();
        }
    }

    public static NewCookie createTokenCookie(String tokenValue, TokenType tokenType) {
        Objects.requireNonNull(tokenValue);
        Objects.requireNonNull(tokenType);
        return new CookieBuilder()
                .withName(tokenType == TokenType.REFRESH ? AUTH_REFRESH_TOKEN : AUTH_ACCESS_TOKEN)
                .withValue(tokenValue)
                .withMaxAge(Integer.MAX_VALUE)
                .withHttpOnly(true)
                .withPath(Constants.API_PATH)
                .buildNewCookie();
    }

}
