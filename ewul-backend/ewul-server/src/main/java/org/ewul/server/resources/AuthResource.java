package org.ewul.server.resources;

import io.dropwizard.hibernate.UnitOfWork;
import org.ewul.core.modules.auth.TokenType;
import org.ewul.core.service.AuthService;
import org.ewul.model.db.auth.Account;
import org.ewul.model.rest.request.auth.LoginRequest;
import org.ewul.model.rest.request.auth.RegisterRequest;
import org.ewul.server.auth.AuthCookies;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final AuthService authService;

    @Inject
    public AuthResource(AuthService authService) {
        this.authService = Objects.requireNonNull(authService);
    }

    @PermitAll
    @GET
    @Path("/test")
    public boolean test() {
        return true;
    }

    @UnitOfWork
    @POST
    @Path("/register")
    public Response register(@Valid RegisterRequest request) {

        Account account;
        try {
            account = authService.register(request.getEmail(), request.getName(), request.getPassword());
        } catch (Exception ex) {
            throw new WebApplicationException(ex, Response.Status.BAD_REQUEST);
        }

        return Response.ok(account.getName()).build();
    }

    @UnitOfWork
    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest request) {

        Optional<Account> account = authService.login(request.getEmail(), request.getPassword());
        if (!account.isPresent()) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        Map<TokenType, String> tokens = authService.createTokens(account.get());

        NewCookie refreshTokenCookie = AuthCookies.createTokenCookie(tokens.get(TokenType.REFRESH), TokenType.REFRESH);
        NewCookie accessTokenCookie = AuthCookies.createTokenCookie(tokens.get(TokenType.ACCESS), TokenType.ACCESS);

        return Response.ok().cookie(refreshTokenCookie, accessTokenCookie).build();
    }

    @UnitOfWork
    @GET
    @Path("/refresh")
    public Response refresh(@CookieParam(AuthCookies.AUTH_REFRESH_TOKEN) String refreshToken) {

        Optional<String> accessToken = authService.refreshAccessToken(refreshToken);
        if (!accessToken.isPresent()) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        NewCookie accessTokenCookie = AuthCookies.createTokenCookie(accessToken.get(), TokenType.ACCESS);

        return Response.ok().cookie(accessTokenCookie).build();
    }

}
