package org.ewul.core.service;

import org.ewul.core.dao.PasswordDAO;
import org.ewul.core.dao.UserAccountDAO;
import org.ewul.core.jwt.AccountJwtHandler;
import org.ewul.model.config.CoreConfiguration;
import org.ewul.model.db.Password;
import org.ewul.model.db.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Singleton
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    protected final CoreConfiguration configuration;
    protected final UserAccountDAO userAccountDAO;
    protected final PasswordDAO passwordDAO;
    protected final AccountJwtHandler accountJwtHandler;

    @Inject
    public AuthService(CoreConfiguration configuration,
                       UserAccountDAO userAccountDAO,
                       PasswordDAO passwordDAO,
                       AccountJwtHandler accountJwtHandler) {

        this.configuration = Objects.requireNonNull(configuration);
        this.userAccountDAO = Objects.requireNonNull(userAccountDAO);
        this.passwordDAO = Objects.requireNonNull(passwordDAO);
        this.accountJwtHandler = Objects.requireNonNull(accountJwtHandler);
    }

    protected boolean checkPassword(String plain, Password password) {
        // TODO implementation
        return true;
    }

    protected void update(UserAccount account) {
        account.setLastAccess(LocalDateTime.now());
    }

    public Optional<UserAccount> register(String email, String name, String password) {
        return Optional.empty();
    }

    public Optional<UserAccount> login(String email, String password, Predicate<UserAccount> filter) {

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("param email");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("param password");
        }
        if (filter == null) {
            throw new IllegalArgumentException("param filter");
        }

        Optional<UserAccount> _account = userAccountDAO.getByEmail(email);
        if (_account.isPresent()) {
            UserAccount account = _account.get();
            if (filter.test(account) && checkPassword(password, account.getPassword())) {
                update(account);
                return Optional.of(account);
            }
            log.info("invalid login attempt: {}", email);
        }

        return Optional.empty();
    }

    public Optional<UserAccount> login(String email, String password) {
        return login(email, password, Objects::nonNull);
    }

    public String generateJwt(UserAccount account) {
        return accountJwtHandler.generateAccountJwt(account);
    }

}
