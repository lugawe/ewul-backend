package org.ewul.core.entity;

import com.querydsl.jpa.JPQLQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public interface EntityDataHandler {

    JPQLQueryFactory createQueryFactory();

    EntityManager provide();

    EntityManagerFactory getFactory();

    String getName();

}
