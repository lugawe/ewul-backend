package org.ewul.core.inject;

import com.google.inject.AbstractModule;
import org.ewul.core.config.CoreConfiguration;
import org.ewul.core.entity.EntityManagerHandler;

import java.util.Objects;

public class CoreModule extends AbstractModule {

    private final CoreConfiguration configuration;
    private final EntityManagerHandler handler;

    public CoreModule(CoreConfiguration configuration, EntityManagerHandler handler) {
        this.configuration = Objects.requireNonNull(configuration);
        this.handler = Objects.requireNonNull(handler);
    }

    @Override
    protected void configure() {
        bind(CoreConfiguration.class).toInstance(configuration);
        bind(EntityManagerHandler.class).toInstance(handler);
    }

}
