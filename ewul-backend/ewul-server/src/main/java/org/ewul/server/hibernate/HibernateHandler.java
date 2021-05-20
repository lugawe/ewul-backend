package org.ewul.server.hibernate;

import org.ewul.core.entity.EntityManagerHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class HibernateHandler implements EntityManagerHandler {

    private static final Logger log = LoggerFactory.getLogger(HibernateHandler.class);

    private final SessionFactory sessionFactory;

    public HibernateHandler(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory);
    }

    @Override
    public String getName() {
        return "hibernate-handler";
    }

    @Override
    public SessionFactory getFactory() {
        log.debug("getFactory");
        return sessionFactory;
    }

    @Override
    public Session get() {
        log.debug("get");
        return sessionFactory.getCurrentSession();
    }

}
