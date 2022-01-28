package org.ewul.core.dao;

import com.querydsl.core.dml.DeleteClause;
import com.querydsl.core.dml.InsertClause;
import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.JPQLQuery;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.core.entity.QueryFactory;
import org.ewul.model.db.DbModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

public abstract class BaseDAO<T extends DbModel> {

    private static final Logger log = LoggerFactory.getLogger(BaseDAO.class);

    protected final Class<T> entityClass;
    protected final EntityDataHandler handler;

    protected BaseDAO(Class<T> entityClass, EntityDataHandler handler) {
        this.entityClass = Objects.requireNonNull(entityClass);
        this.handler = Objects.requireNonNull(handler);
    }

    public final QueryFactory factory() {
        log.debug("{}: create factory", this);
        return handler.createQueryFactory();
    }

    public InsertClause<?> insert(EntityPath<?> path) {
        if (path == null) {
            throw new NullPointerException("path");
        }
        return factory().insert(path);
    }

    public Serializable insert(T entity) {
        if (entity == null) {
            throw new NullPointerException("entity");
        }
        return factory().insert(entity);
    }

    public UpdateClause<?> update(EntityPath<?> path) {
        if (path == null) {
            throw new NullPointerException("path");
        }
        return factory().update(path);
    }

    public void update(T entity) {
        if (entity == null) {
            throw new NullPointerException("entity");
        }
        factory().update(entity);
    }

    public DeleteClause<?> delete(EntityPath<?> path) {
        if (path == null) {
            throw new NullPointerException("path");
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
        return String.format("BaseDAO(entityClass=%s, handler=%s)", entityClass.getSimpleName(), handler.getName());
    }

    public final Class<T> getEntityClass() {
        return entityClass;
    }

    public final EntityDataHandler getHandler() {
        return handler;
    }

}
