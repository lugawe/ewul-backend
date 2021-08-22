package org.ewul.core.entity;

import com.querydsl.jpa.JPQLQueryFactory;
import org.ewul.model.db.DbModel;

import java.util.UUID;

public interface EntityQueryFactory extends JPQLQueryFactory {

    <T extends DbModel> UUID insert(T entity);

}
