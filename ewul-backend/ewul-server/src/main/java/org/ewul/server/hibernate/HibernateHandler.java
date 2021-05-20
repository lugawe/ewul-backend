package org.ewul.server.hibernate;

import org.ewul.core.entity.EntityManagerHandler;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Objects;

public class HibernateHandler implements EntityManagerHandler {

    private final SessionFactory sessionFactory;

    public HibernateHandler(SessionFactory sessionFactory) {
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
