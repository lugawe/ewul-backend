package org.ewul.server.hibernate;

import com.google.inject.AbstractModule;
import org.ewul.core.entity.EntityDataHandler;
import org.hibernate.SessionFactory;

import javax.inject.Provider;
import java.util.Objects;

public class HibernateModule extends AbstractModule {

    private final Provider<SessionFactory> sessionFactoryProvider;

    public HibernateModule(Provider<SessionFactory> sessionFactoryProvider) {
        this.sessionFactoryProvider = Objects.requireNonNull(sessionFactoryProvider);
    }

    public HibernateModule(HibernateBundle hibernateBundle) {
        this(hibernateBundle::getSessionFactory);
    }

    @Override
    protected void configure() {
        bind(EntityDataHandler.class).to(HibernateHandler.class);
        bind(SessionFactory.class).toProvider(sessionFactoryProvider);
    }

}
