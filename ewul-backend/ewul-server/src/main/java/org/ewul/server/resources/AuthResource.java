package org.ewul.server.resources;

import org.ewul.core.service.AuthService;
import org.ewul.model.db.UserAccount;
import org.ewul.model.request.LoginRequest;
import org.ewul.model.request.RegisterRequest;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    @POST
    @Path("/register")
    public Response register(@Valid RegisterRequest request) {

        Optional<UserAccount> account = authService.register(request.getEmail(), request.getName(), request.getPassword());
        if (!account.isPresent()) {
            throw new WebApplicationException(Response.status(400).build());
        }

        return Response.ok().build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest request) {

        Optional<UserAccount> account = authService.login(request.getEmail(), request.getPassword());
        if (!account.isPresent()) {
            throw new WebApplicationException(Response.status(401).build());
        }

        return Response.ok().build();
    }

}
