package org.ewul.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.ewul.core.config.CoreConfiguration;
import org.ewul.core.inject.CoreModule;
import org.ewul.core.service.UserAccountService;
import org.ewul.server.hibernate.HibernateHandler;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class EwulServerConfig {

    private static final Logger log = LoggerFactory.getLogger(EwulServerConfig.class);

    private final HibernateHandler hibernateHandler;
    private final Injector injector;

    @Inject
    public EwulServerConfig(SessionFactory sessionFactory) {
        this.hibernateHandler = new HibernateHandler(sessionFactory);
        this.injector = Guice.createInjector(new CoreModule(CoreConfiguration.empty(), hibernateHandler));
    }

    @Bean
    public HibernateHandler hibernateHandler() {
        return hibernateHandler;
    }

    @Bean
    public Injector injector() {
        return injector;
    }

    @Bean
    public UserAccountService userAccountService() {
        return injector.getInstance(UserAccountService.class);
    }

}
