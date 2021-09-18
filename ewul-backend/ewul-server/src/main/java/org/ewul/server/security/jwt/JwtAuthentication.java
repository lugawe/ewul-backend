package org.ewul.server.security.jwt;

import org.ewul.core.util.Lazy;
import org.ewul.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class JwtAuthentication implements Authentication {

    private static class AuthoritiesSupplier implements Supplier<Set<GrantedAuthority>> {

        private final User user;

        public AuthoritiesSupplier(User user) {
            this.user = user;
        }

        private Collection<String> emptyIfNull(Collection<String> collection) {
            return collection != null ? collection : Collections.emptySet();
        }

        @Override
        public Set<GrantedAuthority> get() {
            Set<GrantedAuthority> result = emptyIfNull(user.getRoles())
                    .stream()
                    .filter(Objects::nonNull)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
            return Collections.unmodifiableSet(result);
        }

    }

    protected final User user;
    protected final Lazy<Set<GrantedAuthority>> authorities;
    protected final String jwtToken;

    protected Object details;
    protected boolean authenticated;

    protected JwtAuthentication(User user, String jwtToken) {
        this.user = Objects.requireNonNull(user);
        this.authorities = Lazy.of(new AuthoritiesSupplier(user));
        this.jwtToken = Objects.requireNonNull(jwtToken);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.get();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public User getUser() {
        return user;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public static JwtAuthentication create(User user, String jwtToken) {
        return new JwtAuthentication(user, jwtToken);
    }

}
