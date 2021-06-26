package org.ewul.server.hibernate;

import org.ewul.core.entity.TransactionHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class HibernateHandler implements TransactionHandler {

    private static final Logger log = LoggerFactory.getLogger(HibernateHandler.class);

    private final SessionFactory sessionFactory;

    @Inject
    public HibernateHandler(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory);
    }

    @Override
    public Session provide() {
        log.debug("provide");
        return sessionFactory.getCurrentSession();
    }

    @Override
    public SessionFactory getFactory() {
        return sessionFactory;
    }

    @Override
    public String getName() {
        return "hibernate-transaction-handler";
    }

    @Override
    public void close() {
        log.debug("close");
        sessionFactory.close();
    }

}
