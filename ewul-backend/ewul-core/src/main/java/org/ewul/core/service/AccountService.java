package org.ewul.core.service;


import org.ewul.model.config.CoreConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Objects;

@Singleton
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final CoreConfiguration configuration;

    @Inject
    public AccountService(CoreConfiguration configuration) {
        this.configuration = Objects.requireNonNull(configuration);
    }

}
