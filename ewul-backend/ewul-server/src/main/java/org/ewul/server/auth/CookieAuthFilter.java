package org.ewul.server.auth;

import io.dropwizard.auth.DefaultUnauthorizedHandler;
import io.dropwizard.auth.UnauthorizedHandler;
import org.ewul.core.modules.auth.AuthManager;
import org.ewul.core.modules.auth.TokenType;
import org.ewul.core.util.NullUtils;
import org.ewul.model.User;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Priority(Priorities.AUTHENTICATION)
public class CookieAuthFilter implements ContainerRequestFilter {

    private static final String PREFIX = "AuthUser";
    private static final String REALM = "realm";

    private final UnauthorizedHandler unauthorizedHandler = new DefaultUnauthorizedHandler();

    private final AuthManager authManager;
    private final UserAuthenticator userAuthenticator;
    private final UserAuthorizer userAuthorizer;

    public CookieAuthFilter(AuthManager authManager) {
        this.authManager = Objects.requireNonNull(authManager);
        this.userAuthenticator = new UserAuthenticator(this.authManager);
        this.userAuthorizer = new UserAuthorizer(this.authManager);
    }

    private SecurityContext createSecurityContext(ContainerRequestContext ctx, User user) {
        boolean isSecure = NullUtils.safeGet(() -> ctx.getSecurityContext().isSecure(), false);
        return new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return user;
            }

            @Override
            public boolean isUserInRole(String role) {
                return userAuthorizer.authorize(user, role, ctx);
            }

            @Override
            public boolean isSecure() {
                return isSecure;
            }

            @Override
            public String getAuthenticationScheme() {
                return SecurityContext.BASIC_AUTH;
            }
        };
    }

    private Optional<User> tryAuthenticate(Map<TokenType, String> tokens) {
        return userAuthenticator.authenticate(tokens.get(TokenType.ACCESS));
    }

    @Override
    public void filter(ContainerRequestContext ctx) {

        try {

            Map<TokenType, String> tokens = AuthCookies.extractTokens(ctx.getCookies());
            if (tokens.isEmpty()) {
                throw new IllegalStateException("no cookies provided");
            }

            Optional<User> authenticatedUser = tryAuthenticate(tokens);
            if (!authenticatedUser.isPresent()) {
                throw new IllegalStateException("invalid");
            }

            ctx.setSecurityContext(createSecurityContext(ctx, authenticatedUser.get()));

        } catch (Exception e) {
            throw new WebApplicationException(e, unauthorizedHandler.buildResponse(PREFIX, REALM));
        }

    }

}
