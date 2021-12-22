package org.ewul.server.hibernate;

import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import org.ewul.core.util.Lazy;
import org.ewul.server.EwulServerConfig;

public class HibernateBundle extends ScanningHibernateBundle<EwulServerConfig> {

    private final static Lazy<HibernateBundle> instance = Lazy.of(HibernateBundle::new);

    public static HibernateBundle getInstance() {
        return instance.get();
    }

    private HibernateBundle() {
        super("org.ewul.model.db");
    }

    @Override
    public PooledDataSourceFactory getDataSourceFactory(EwulServerConfig config) {
        return config.getDataSourceFactory();
    }

}
