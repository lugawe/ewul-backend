package org.ewul.core.service;

import org.ewul.core.config.CoreConfiguration;
import org.ewul.core.dao.PasswordDAO;
import org.ewul.core.dao.UserAccountDAO;
import org.ewul.model.db.Password;
import org.ewul.model.db.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Singleton
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    protected final CoreConfiguration configuration;
    protected final UserAccountDAO userAccountDAO;
    protected final PasswordDAO passwordDAO;

    @Inject
    public AuthService(CoreConfiguration configuration,
                       UserAccountDAO userAccountDAO,
                       PasswordDAO passwordDAO) {

        this.configuration = Objects.requireNonNull(configuration);
        this.userAccountDAO = Objects.requireNonNull(userAccountDAO);
        this.passwordDAO = Objects.requireNonNull(passwordDAO);
    }

    protected boolean checkPassword(String plain, Password password) {
        // TODO implementation
        return true;
    }

    public Optional<UserAccount> login(String name, String password, Predicate<UserAccount> filter) {

        Optional<UserAccount> _account = userAccountDAO.getByName(name);
        if (_account.isPresent()) {
            UserAccount account = _account.get();
            if (filter.test(account) && checkPassword(password, account.getPassword())) {
                return Optional.of(account);
            }
        }

        log.warn("invalid login attempt: {}", name);
        return Optional.empty();
    }

    public Optional<UserAccount> login(String name, String password) {
        return login(name, password, Objects::nonNull);
    }

}
