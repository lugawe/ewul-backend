package org.ewul.core.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.ewul.core.entity.TransactionHandler;
import org.ewul.model.db.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.Objects;

public abstract class BaseDAO<T extends Model> {

    private static final Logger log = LoggerFactory.getLogger(BaseDAO.class);

    protected final Class<T> entityClass;
    protected final TransactionHandler handler;

    protected BaseDAO(Class<T> entityClass, TransactionHandler handler) {
        this.entityClass = Objects.requireNonNull(entityClass);
        this.handler = Objects.requireNonNull(handler);
    }

    protected final EntityManager provide() {
        log.debug("{} provide", handler.getName());
        return handler.provide();
    }

    protected void configure(EntityManager entityManager) {
    }

    public JPAQueryFactory factory() {
        Provider<EntityManager> provider = () -> {
            EntityManager entityManager = provide();
            configure(entityManager);
            return entityManager;
        };
        return new JPAQueryFactory(provider);
    }

    public JPAQuery<?> query(long offset, long limit) {
        JPAQuery<?> query = factory().query();
        if (offset > 0) {
            query = query.offset(offset);
        }
        if (limit > 0) {
            query = query.limit(limit);
        }
        return query;
    }

    public JPAQuery<?> query() {
        return query(0, 0);
    }

    public final Class<T> getEntityClass() {
        return entityClass;
    }

}
