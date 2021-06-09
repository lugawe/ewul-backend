package org.ewul.server;

import com.google.inject.Injector;
import org.ewul.core.Ewul;
import org.ewul.core.config.CoreConfiguration;
import org.ewul.core.service.AuthService;
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

    private final Injector injector;

    @Inject
    public EwulServerConfig(SessionFactory sessionFactory) {
        this.injector = Ewul.injector(CoreConfiguration.empty(), new HibernateHandler(sessionFactory));
    }

    @Bean
    public Injector injector() {
        return injector;
    }

    @Bean
    public AuthService authService() {
        return injector.getInstance(AuthService.class);
    }

}
