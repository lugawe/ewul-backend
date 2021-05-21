package org.ewul.core.service;

import org.ewul.core.config.CoreConfiguration;
import org.ewul.core.dao.UserAccountDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class UserAccountService {

    private final CoreConfiguration configuration;
    private final UserAccountDAO userAccountDAO;

    @Inject
    public UserAccountService(CoreConfiguration configuration, UserAccountDAO userAccountDAO) {
        this.configuration = Objects.requireNonNull(configuration);
        this.userAccountDAO = Objects.requireNonNull(userAccountDAO);
    }

}
