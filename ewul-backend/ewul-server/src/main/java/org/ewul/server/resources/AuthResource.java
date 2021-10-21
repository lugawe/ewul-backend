package org.ewul.server.resources;

import io.dropwizard.hibernate.UnitOfWork;
import org.ewul.core.service.AuthService;
import org.ewul.model.db.UserAccount;
import org.ewul.model.request.LoginRequest;
import org.ewul.model.request.RegisterRequest;
import org.ewul.server.auth.JwtCookie;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.time.Duration;
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

        Optional<UserAccount> account = authService.register(request.getEmail(), request.getName(), request.getPassword());
        if (!account.isPresent()) {
            throw new WebApplicationException(Response.status(400).build());
        }

        return Response.ok().build();
    }

    @UnitOfWork
    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest request) {

        Optional<UserAccount> account = authService.login(request.getEmail(), request.getPassword());
        if (!account.isPresent()) {
            throw new WebApplicationException(Response.status(401).build());
        }

        NewCookie authCookie;
        try {
            String jwt = authService.generateJwt(account.get());
            authCookie = JwtCookie.createDefaultCookie(jwt, Duration.ofDays(30));
        } catch (Exception ex) {
            throw new WebApplicationException(ex, Response.status(401).build());
        }

        return Response.ok().cookie(authCookie).build();
    }

}
