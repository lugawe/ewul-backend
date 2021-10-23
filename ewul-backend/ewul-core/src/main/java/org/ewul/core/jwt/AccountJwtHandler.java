package org.ewul.core.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.ewul.core.util.AccountUtils;
import org.ewul.model.config.CoreConfiguration;
import org.ewul.model.config.JwtConfiguration;
import org.ewul.model.db.Account;

import javax.inject.Inject;
import java.util.Date;
import java.util.Objects;

public class AccountJwtHandler extends JwtHandler {

    public static class Builder extends JwtHandler.Builder {

        public Builder() {
        }

        public Builder withAccount(Account account) {
            this.withAuthId(account.getId());
            this.withAuthName(account.getName());
            this.withRoles(AccountUtils.getRoles(account), true);
            this.withProperties(account.getProperties(), true);
            return this;
        }

    }

    protected final JwtConfiguration jwtConfiguration;

    public AccountJwtHandler(Algorithm algorithm, JwtConfiguration jwtConfiguration) {
        super(algorithm);
        this.jwtConfiguration = Objects.requireNonNull(jwtConfiguration);
    }

    public AccountJwtHandler(JwtConfiguration jwtConfiguration) {
        this(Algorithm.HMAC512(jwtConfiguration.getSecret()), jwtConfiguration);
    }

    @Inject
    public AccountJwtHandler(CoreConfiguration configuration) {
        this(configuration.getJwtConfiguration());
    }

    public String generateAccountJwt(Account account) {

        if (account == null) {
            throw new NullPointerException("account");
        }

        Builder builder = new Builder();

        builder.withIssuer(jwtConfiguration.getIssuer());
        builder.withExpiresAt(new Date(System.currentTimeMillis() + jwtConfiguration.getLifetime().toMillis()));

        builder.withAccount(account);

        return builder.build(algorithm);
    }

}
