package org.ewul.server.security;

import org.ewul.core.jwt.JwtHandler;
import org.ewul.model.User;
import org.ewul.server.security.jwt.UserAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class CookieAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(CookieAuthenticationFilter.class);

    public static final String JWT_COOKIE_NAME = "auth_jwt";

    protected final JwtHandler jwtHandler;

    public CookieAuthenticationFilter(RequestMatcher requestMatcher, JwtHandler jwtHandler) {
        super(requestMatcher);
        this.jwtHandler = Objects.requireNonNull(jwtHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length < 1) {
            throw new AuthenticationServiceException("no cookies provided");
        }

        Optional<Cookie> cookie = Arrays.stream(cookies).filter(c -> JWT_COOKIE_NAME.equals(c.getName())).findFirst();
        if (!cookie.isPresent()) {
            throw new AuthenticationServiceException(String.format("no %s cookie provided", JWT_COOKIE_NAME));
        }

        String jwt = cookie.get().getValue();

        try {
            User holder = Objects.requireNonNull(jwtHandler.decode(jwt));
            UserAuthenticationToken authentication = new UserAuthenticationToken(holder);
            log.info("valid jwt authentication: {}", holder.getId());
            return authentication;
        } catch (Exception ex) {
            throw new AuthenticationServiceException("invalid jwt token", ex);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request,
                                         HttpServletResponse response,
                                         FilterChain chain,
                                         Authentication authentication) throws IOException, ServletException {

        super.successfulAuthentication(request, response, chain, authentication);
    }

}
