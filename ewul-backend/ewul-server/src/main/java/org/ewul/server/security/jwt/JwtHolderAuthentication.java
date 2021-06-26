package org.ewul.server.security.jwt;

import org.ewul.core.jwt.JwtHolder;
import org.ewul.model.db.Account;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

public class JwtHolderAuthentication extends AbstractAuthenticationToken {

    protected final JwtHolder jwtHolder;
    protected Account account;

    public JwtHolderAuthentication(JwtHolder jwtHolder) {
        super(jwtHolder.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
        this.jwtHolder = jwtHolder;
    }

    protected Account buildAccount() {
        if (account == null) {
            account = new Account();
            account.setId(jwtHolder.getAuthId());
        }
        return account;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return buildAccount();
    }

}
