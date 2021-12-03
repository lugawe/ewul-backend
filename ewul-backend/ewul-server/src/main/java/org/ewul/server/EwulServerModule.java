package org.ewul.server;

import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.core.security.hashing.BCryptPasswordHashing;
import org.ewul.core.security.hashing.PasswordHashing;
import org.ewul.core.util.Lazy;
import org.ewul.model.config.CoreConfiguration;
import org.ewul.server.hibernate.HibernateHandler;
import org.hibernate.SessionFactory;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

public class EwulServerModule extends DropwizardAwareModule<EwulServerConfig> {

    public static class EwulHibernateBundle extends ScanningHibernateBundle<EwulServerConfig> {

        public EwulHibernateBundle() {
            super("org.ewul.model.db");
        }

        @Override
        public PooledDataSourceFactory getDataSourceFactory(EwulServerConfig config) {
            return config.getDataSourceFactory();
        }

    }

    public static final Lazy<EwulHibernateBundle> HIBERNATE_BUNDLE = Lazy.of(EwulHibernateBundle::new);
    public static final Lazy<EwulServerModule> INSTANCE = Lazy.of(EwulServerModule::new);

    protected EwulServerModule() {
    }

    @Override
    public void configure() {
        bind(CoreConfiguration.class).toProvider(() -> INSTANCE.get().configuration().getCoreConfiguration());
        bind(SessionFactory.class).toProvider(() -> HIBERNATE_BUNDLE.get().getSessionFactory());
        bind(EntityDataHandler.class).to(HibernateHandler.class);
        bind(PasswordHashing.class).toProvider(BCryptPasswordHashing::getInstance);
    }

}
