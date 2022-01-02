package org.ewul.core.entity;

import javax.persistence.EntityManagerFactory;

public interface EntityDataHandler {

    QueryFactory createQueryFactory();

    EntityManagerFactory getFactory();

    String getName();

}
