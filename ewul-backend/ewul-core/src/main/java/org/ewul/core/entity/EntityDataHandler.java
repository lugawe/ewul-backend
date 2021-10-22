package org.ewul.core.entity;

import javax.persistence.EntityManagerFactory;

public interface EntityDataHandler {

    EntityQueryFactory createQueryFactory();

    EntityManagerFactory getFactory();

    String getName();

}
