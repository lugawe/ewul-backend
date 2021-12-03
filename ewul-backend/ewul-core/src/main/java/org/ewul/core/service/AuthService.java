package org.ewul.core.service;

import org.ewul.core.dao.auth.AccountDAO;
import org.ewul.core.dao.auth.PasswordDAO;
import org.ewul.core.jwt.AccountJwtHandler;
import org.ewul.model.config.CoreConfiguration;
import org.ewul.model.db.auth.Account;
import org.ewul.model.db.auth.Password;
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
    protected final AccountDAO accountDAO;
    protected final PasswordDAO passwordDAO;
    protected final AccountJwtHandler accountJwtHandler;

    @Inject
    public AuthService(CoreConfiguration configuration,
                       AccountDAO accountDAO,
                       PasswordDAO passwordDAO,
                       AccountJwtHandler accountJwtHandler) {

        this.configuration = Objects.requireNonNull(configuration);
        this.accountDAO = Objects.requireNonNull(accountDAO);
        this.passwordDAO = Objects.requireNonNull(passwordDAO);
        this.accountJwtHandler = Objects.requireNonNull(accountJwtHandler);
    }

    public Account register(String email, String name, String plainPassword) {

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("param email");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("param name");
        }
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("param plainPassword");
        }

        if (accountDAO.checkEmailExists(email)) {
            throw new IllegalStateException("email already exists");
        }
        if (accountDAO.checkNameExists(name)) {
            throw new IllegalStateException("name already exists");
        }

        LocalDateTime now = LocalDateTime.now();

        Account account = new Account();
        account.setEmail(email);
        account.setName(name);
        account.setCreatedAt(now);
        account.setLastAccess(now);

        Password password = passwordDAO.createPassword(plainPassword);
        account.setPassword(password);

        if (accountDAO.factory().insert(account) == null) {
            throw new IllegalStateException("cannot insert account entity");
        }

        return account;
    }

    protected void update(Account account) {
        account.setLastAccess(LocalDateTime.now());
    }

    public Optional<Account> login(String email, String password, Predicate<Account> filter) {

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("param email");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("param password");
        }
        if (filter == null) {
            throw new IllegalArgumentException("param filter");
        }

        Optional<Account> _account = accountDAO.getByEmail(email);
        if (_account.isPresent()) {
            Account account = _account.get();
            if (filter.test(account) && passwordDAO.checkPassword(password, account.getPassword())) {
                update(account);
                return Optional.of(account);
            }
            log.info("invalid login attempt: {}", email);
        }

        return Optional.empty();
    }

    public Optional<Account> login(String email, String password) {
        return login(email, password, Objects::nonNull);
    }

    public String generateJwt(Account account) {
        return accountJwtHandler.generateAccountJwt(account);
    }

}
