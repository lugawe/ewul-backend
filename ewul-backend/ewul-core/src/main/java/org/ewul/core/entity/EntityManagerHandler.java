package org.ewul.core.entity;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public interface EntityManagerHandler extends Provider<EntityManager> {

    String getName();

    EntityManagerFactory getFactory();

}
