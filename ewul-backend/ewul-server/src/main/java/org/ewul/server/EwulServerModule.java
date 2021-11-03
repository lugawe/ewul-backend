package org.ewul.server;

import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.core.util.Lazy;
import org.ewul.model.config.CoreConfiguration;
import org.ewul.model.db.DbModel;
import org.ewul.model.db.auth.Account;
import org.ewul.model.db.auth.Group;
import org.ewul.model.db.auth.Password;
import org.ewul.model.db.auth.Role;
import org.ewul.server.hibernate.HibernateHandler;
import org.hibernate.SessionFactory;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

public class EwulServerModule extends DropwizardAwareModule<EwulServerConfig> {

    public static class EwulHibernateBundle extends HibernateBundle<EwulServerConfig> {

        public static final Class<?>[] ENTITIES = {
                Account.class,
                Group.class,
                Password.class,
                Role.class
        };

        public EwulHibernateBundle() {
            super(DbModel.class, ENTITIES);
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
    }

}
