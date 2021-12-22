package org.ewul.server.hibernate;

import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import org.ewul.server.EwulServerConfig;

public class HibernateBundle extends ScanningHibernateBundle<EwulServerConfig> {

    public static final String ENTITY_PACKAGE = "org.ewul.model.db";

    public HibernateBundle() {
        super(ENTITY_PACKAGE);
    }

    @Override
    public PooledDataSourceFactory getDataSourceFactory(EwulServerConfig config) {
        return config.getDataSourceFactory();
    }

}
