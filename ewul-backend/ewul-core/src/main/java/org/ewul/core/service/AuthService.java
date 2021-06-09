package org.ewul.core.service;

import org.ewul.core.config.CoreConfiguration;
import org.ewul.core.dao.UserAccountDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    protected final CoreConfiguration configuration;
    protected final UserAccountDAO userAccountDAO;

    @Inject
    public AuthService(CoreConfiguration configuration, UserAccountDAO userAccountDAO) {
        this.configuration = Objects.requireNonNull(configuration);
        this.userAccountDAO = Objects.requireNonNull(userAccountDAO);
    }

}
