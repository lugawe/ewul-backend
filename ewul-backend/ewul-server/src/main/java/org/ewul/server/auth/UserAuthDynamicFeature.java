package org.ewul.server.auth;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.setup.Environment;
import org.ewul.core.modules.auth.AuthManager;
import org.ewul.model.User;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.inject.Inject;
import javax.ws.rs.ext.Provider;

@Provider
public class UserAuthDynamicFeature extends AuthDynamicFeature {

    @Inject
    public UserAuthDynamicFeature(Environment environment, AuthManager authManager) {
        super(new CookieAuthFilter(authManager));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

}
