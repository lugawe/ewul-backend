package org.ewul.core.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public interface EntityDataHandler {

    EntityQueryFactory createQueryFactory();

    EntityManager provide();

    EntityManagerFactory getFactory();

    String getName();

}
