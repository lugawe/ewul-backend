package org.ewul.core.entity;

import com.querydsl.jpa.JPQLQueryFactory;

import java.io.Serializable;

public interface EntityQueryFactory extends JPQLQueryFactory {

    <T> Serializable insert(T entity);

}
