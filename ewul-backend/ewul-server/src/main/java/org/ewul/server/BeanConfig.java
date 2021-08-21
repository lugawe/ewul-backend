package org.ewul.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.core.inject.CoreModule;
import org.ewul.core.service.AuthService;
import org.ewul.server.hibernate.HibernateHandler;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import java.util.Objects;

@Configuration
public class BeanConfig {

    protected final Injector injector;

    public BeanConfig(Injector injector) {
        this.injector = Objects.requireNonNull(injector, "param injector");
    }

    public BeanConfig(Module... modules) {
        this(Guice.createInjector(modules));
    }

    @Inject
    public BeanConfig(EwulServerConfig config, SessionFactory sessionFactory) {
        this(new CoreModule(config.getCoreConfiguration(), new HibernateHandler(sessionFactory)));
    }

    @Bean
    public Injector injector() {
        return injector;
    }

    @Bean
    public EntityDataHandler transactionHandler() {
        return injector.getInstance(EntityDataHandler.class);
    }

    @Bean
    public AuthService authService() {
        return injector.getInstance(AuthService.class);
    }

}
