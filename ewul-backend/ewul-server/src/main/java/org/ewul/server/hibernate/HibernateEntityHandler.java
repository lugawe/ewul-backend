package org.ewul.server.hibernate;

import org.ewul.core.management.EntityHandler;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Objects;

public class HibernateEntityHandler implements EntityHandler {

    private final SessionFactory sessionFactory;

    public HibernateEntityHandler(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory);
    }

    @Override
    public EntityManagerFactory getFactory() {
        return sessionFactory;
    }

    @Override
    public EntityManager get() {
        return sessionFactory.getCurrentSession();
    }

}
