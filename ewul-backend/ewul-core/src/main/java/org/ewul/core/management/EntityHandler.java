package org.ewul.core.management;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public interface EntityHandler extends Provider<EntityManager> {

    EntityManagerFactory getFactory();

}
