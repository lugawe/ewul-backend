package org.ewul.core.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.Closeable;

public interface TransactionHandler extends Closeable {

    EntityManager provide();

    EntityManagerFactory getFactory();

    String getName();

}
