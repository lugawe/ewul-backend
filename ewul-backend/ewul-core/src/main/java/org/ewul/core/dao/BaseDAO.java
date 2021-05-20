package org.ewul.core.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.ewul.core.entity.EntityManagerHandler;
import org.ewul.model.db.Model;

import javax.persistence.EntityManager;
import java.util.Objects;

public abstract class BaseDAO<T extends Model> {

    protected final Class<T> entityClass;
    protected final EntityManagerHandler handler;

    protected BaseDAO(Class<T> entityClass, EntityManagerHandler handler) {
        this.entityClass = Objects.requireNonNull(entityClass);
        this.handler = Objects.requireNonNull(handler);
    }

    protected EntityManager provide() {
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

    public final Class<T> getEntityClass() {
        return entityClass;
    }

}
