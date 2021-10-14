package org.ewul.server.hibernate;

import org.ewul.core.entity.EntityDataHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Objects;

public class HibernateHandler implements EntityDataHandler {

    private static final Logger log = LoggerFactory.getLogger(HibernateHandler.class);

    private final SessionFactory sessionFactory;

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
        Session session = sessionFactory.getCurrentSession();
        if (session == null) {
            log.error("no session provided: {}", sessionFactory);
            throw new NullPointerException("no session provided");
        }
        return session.unwrap(Session.class);
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
