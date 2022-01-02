package org.ewul.server.hibernate;

import com.querydsl.jpa.HQLTemplates;
import com.querydsl.jpa.JPQLTemplates;
import org.ewul.core.entity.QueryFactory;
import org.ewul.model.db.DbModel;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

public class HibernateQueryFactory extends com.querydsl.jpa.hibernate.HibernateQueryFactory
        implements QueryFactory {

    private static final Logger log = LoggerFactory.getLogger(HibernateQueryFactory.class);

    private final Supplier<Session> session;

    public HibernateQueryFactory(Session session) {
        this(HQLTemplates.DEFAULT, () -> session);
    }

    public HibernateQueryFactory(JPQLTemplates templates, Session session) {
        this(templates, () -> session);
    }

    public HibernateQueryFactory(Supplier<Session> session) {
        this(HQLTemplates.DEFAULT, session);
    }

    public HibernateQueryFactory(JPQLTemplates templates, Supplier<Session> session) {
        super(Objects.requireNonNull(templates), Objects.requireNonNull(session));
        this.session = session;
        log.debug("new hibernate-query-factory created");
    }

    @Override
    public <T extends DbModel> Serializable insert(T entity) {
        if (entity == null) {
            throw new NullPointerException("entity");
        }
        return session.get().save(entity);
    }

    @Override
    public <T extends DbModel> void update(T entity) {
        if (entity == null) {
            throw new NullPointerException("entity");
        }
        session.get().update(entity);
    }

}
