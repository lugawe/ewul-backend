package org.ewul.core.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.ewul.core.management.EntityHandler;
import org.ewul.model.db.Model;

import javax.persistence.EntityManager;
import java.util.Objects;

public abstract class BaseDAO<T extends Model> {

    protected final Class<T> entityClass;
    protected final EntityHandler entityHandler;

    protected BaseDAO(Class<T> entityClass, EntityHandler entityHandler) {
        this.entityClass = Objects.requireNonNull(entityClass);
        this.entityHandler = Objects.requireNonNull(entityHandler);
    }

    protected EntityManager provide() {
        return entityHandler.get();
    }

    public JPAQueryFactory factory() {
        return new JPAQueryFactory(provide());
    }

    public final Class<T> getEntityClass() {
        return entityClass;
    }

}
