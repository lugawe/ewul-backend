package org.ewul.server.security.jwt;

import org.ewul.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

    protected final User user;

    public UserAuthenticationToken(User user) {
        super(user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
        this.user = user;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

}
