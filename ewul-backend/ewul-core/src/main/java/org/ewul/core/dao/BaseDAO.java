package org.ewul.core.dao;

import com.querydsl.core.dml.DeleteClause;
import com.querydsl.core.dml.InsertClause;
import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.JPQLQuery;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.core.entity.EntityQueryFactory;
import org.ewul.model.db.DbModel;

import java.util.Objects;
import java.util.UUID;

public abstract class BaseDAO<T extends DbModel> {

    protected final Class<T> entityClass;
    protected final EntityDataHandler handler;

    protected BaseDAO(Class<T> entityClass, EntityDataHandler handler) {
        this.entityClass = Objects.requireNonNull(entityClass);
        this.handler = Objects.requireNonNull(handler);
    }

    public final EntityQueryFactory factory() {
        return handler.createQueryFactory();
    }

    public InsertClause<?> insert(EntityPath<?> path) {
        if (path == null) {
            throw new NullPointerException("param path");
        }
        return factory().insert(path);
    }

    public UUID insert(T entity) {
        if (entity == null) {
            throw new NullPointerException("param entity");
        }
        return factory().insert(entity);
    }

    public UpdateClause<?> update(EntityPath<?> path) {
        if (path == null) {
            throw new NullPointerException("param path");
        }
        return factory().update(path);
    }

    public DeleteClause<?> delete(EntityPath<?> path) {
        if (path == null) {
            throw new NullPointerException("param path");
        }
        return factory().delete(path);
    }

    public JPQLQuery<?> query(long offset, long limit) {
        JPQLQuery<?> query = factory().query();
        if (offset > 0) {
            query = query.offset(offset);
        }
        if (limit > 0) {
            query = query.limit(limit);
        }
        return query;
    }

    public JPQLQuery<?> query() {
        return query(0, 0);
    }

    @Override
    public String toString() {
        return String.format("BaseDAO{ %s - %s }", entityClass.getSimpleName(), handler.getName());
    }

    public final Class<T> getEntityClass() {
        return entityClass;
    }

    public final EntityDataHandler getHandler() {
        return handler;
    }

}
