package org.ewul.core.entity;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.Closeable;

public interface EntityManagerHandler extends Provider<EntityManager>, Closeable {

    EntityManagerFactory getFactory();

    String getName();

}
