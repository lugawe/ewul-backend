package org.ewul.core.service;

import org.ewul.core.cache.Caches;
import org.ewul.core.dao.auth.AccountDAO;
import org.ewul.core.dao.auth.GroupDAO;
import org.ewul.core.dao.auth.PasswordDAO;
import org.ewul.core.dao.auth.RoleDAO;
import org.ewul.core.modules.auth.AuthManager;
import org.ewul.core.modules.auth.TokenType;
import org.ewul.core.modules.password.PasswordManager;
import org.ewul.model.User;
import org.ewul.model.config.CoreConfiguration;
import org.ewul.model.db.auth.Account;
import org.ewul.model.db.auth.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

@Singleton
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    protected final CoreConfiguration configuration;

    protected final AccountDAO accountDAO;
    protected final GroupDAO groupDAO;
    protected final PasswordDAO passwordDAO;
    protected final RoleDAO roleDAO;

    protected final AuthManager authManager;
    protected final PasswordManager passwordManager;

    @Inject
    public AuthService(CoreConfiguration configuration,
                       AccountDAO accountDAO,
                       GroupDAO groupDAO,
                       PasswordDAO passwordDAO,
                       RoleDAO roleDAO,
                       AuthManager authManager,
                       PasswordManager passwordManager) {

        this.configuration = Objects.requireNonNull(configuration);
        this.accountDAO = Objects.requireNonNull(accountDAO);
        this.groupDAO = Objects.requireNonNull(groupDAO);
        this.passwordDAO = Objects.requireNonNull(passwordDAO);
        this.roleDAO = Objects.requireNonNull(roleDAO);
        this.authManager = Objects.requireNonNull(authManager);
        this.passwordManager = Objects.requireNonNull(passwordManager);
    }

    public Map<TokenType, String> createTokens(Account account) {

        String refreshToken = authManager.generateRefreshToken(account.getId());
        String accessToken = refreshAccessToken(refreshToken).orElse(null);

        Map<TokenType, String> result = new LinkedHashMap<>();
        result.put(TokenType.REFRESH, refreshToken);
        result.put(TokenType.ACCESS, accessToken);

        return Collections.unmodifiableMap(result);
    }

    public Optional<String> refreshAccessToken(String refreshToken) {

        Optional<UUID> id = authManager.decodeRefreshToken(refreshToken);
        if (!id.isPresent()) {
            return Optional.empty();
        }

        User user = Caches.USER_CACHE.getUnchecked(id.get());

        return Optional.of(authManager.generateAccessToken(user));
    }

    public Account register(String email, String name, String plainPassword) {

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("email");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name");
        }
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("plainPassword");
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

        String hashedPassword = passwordManager.hash(plainPassword);
        Password password = passwordDAO.createPassword(hashedPassword);
        account.setPassword(password);

        if (accountDAO.factory().insert(account) == null) {
            throw new IllegalStateException("cannot insert account entity");
        }

        return account;
    }

    protected void update(Account account) {
        account.setLastAccess(LocalDateTime.now());
    }

    public Optional<Account> login(String email, String plainPassword, Predicate<Account> filter) {

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("email");
        }
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("plainPassword");
        }
        if (filter == null) {
            throw new IllegalArgumentException("filter");
        }

        Optional<Account> _account = accountDAO.getByEmail(email);
        if (_account.isPresent()) {
            Account account = _account.get();
            if (filter.test(account)) {
                Password password = account.getPassword();
                if (password != null && passwordManager.check(plainPassword, password.getHash())) {
                    update(account);
                    return Optional.of(account);
                }
            }
            log.info("invalid login attempt: {}", email);
        }

        return Optional.empty();
    }

    public Optional<Account> login(String email, String password) {
        return login(email, password, Objects::nonNull);
    }

}
