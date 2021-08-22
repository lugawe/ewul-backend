package org.ewul.server.hibernate;

import org.ewul.core.entity.EntityDataHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;

@Singleton
public class HibernateHandler implements EntityDataHandler {

    private static final Logger log = LoggerFactory.getLogger(HibernateHandler.class);

    private final SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    public HibernateHandler(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory);
    }

    @Override
    public HibernateQueryFactory createQueryFactory() {
        return new HibernateQueryFactory(this::provide);
    }

    @Override
    public Session provide() {
        log.debug("provide");
        if (entityManager == null) {
            throw new NullPointerException("PersistenceContext: entity manager is null");
        }
        return entityManager.unwrap(Session.class);
    }

    @Override
    public SessionFactory getFactory() {
        return sessionFactory;
    }

    @Override
    public String getName() {
        return "hibernate-handler";
    }

}
