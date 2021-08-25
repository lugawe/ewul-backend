package org.ewul.server.security.jwt;

import org.ewul.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

    protected final User user;

    protected UserAuthenticationToken(Collection<? extends GrantedAuthority> authorities, User user) {
        super(authorities);
        this.user = Objects.requireNonNull(user);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    public static UserAuthenticationToken create(User user) {
        Set<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .filter(role -> role != null && !role.isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return new UserAuthenticationToken(authorities, user);
    }

}
