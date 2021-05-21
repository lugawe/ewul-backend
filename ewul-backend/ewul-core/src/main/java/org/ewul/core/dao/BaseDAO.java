package org.ewul.core.dao;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.ewul.core.entity.EntityManagerHandler;
import org.ewul.model.db.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.Objects;

public abstract class BaseDAO<T extends Model> {

    private static final Logger log = LoggerFactory.getLogger(BaseDAO.class);

    protected final Class<T> entityClass;
    protected final EntityPath<T> entityPath;
    protected final EntityManagerHandler handler;

    protected BaseDAO(Class<T> entityClass, EntityPath<T> entityPath, EntityManagerHandler handler) {
        this.entityClass = Objects.requireNonNull(entityClass);
        this.entityPath = Objects.requireNonNull(entityPath);
        this.handler = Objects.requireNonNull(handler);
    }

    protected final EntityManager provide() {
        log.debug("provide");
        return handler.get();
    }

    protected void configure(EntityManager entityManager) {
    }

    public JPAQueryFactory factory() {
        EntityManager entityManager = provide();
        configure(entityManager);
        return new JPAQueryFactory(entityManager);
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

    public JPAUpdateClause update() {
        return factory().update(entityPath);
    }

    public JPADeleteClause delete() {
        return factory().delete(entityPath);
    }

    public final Class<T> getEntityClass() {
        return entityClass;
    }

}
