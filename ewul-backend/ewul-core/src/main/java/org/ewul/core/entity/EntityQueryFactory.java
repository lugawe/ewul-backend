package org.ewul.core.entity;

import com.querydsl.jpa.JPQLQueryFactory;

import java.util.UUID;

public interface EntityQueryFactory extends JPQLQueryFactory {

    <T> UUID insert(T entity);

}
