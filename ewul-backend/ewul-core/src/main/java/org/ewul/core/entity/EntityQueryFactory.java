package org.ewul.core.entity;

import com.querydsl.jpa.JPQLQueryFactory;
import org.ewul.model.db.DbModel;

import java.io.Serializable;

public interface EntityQueryFactory extends JPQLQueryFactory {

    <T extends DbModel> Serializable insert(T entity);

    <T extends DbModel> void update(T entity);

}
