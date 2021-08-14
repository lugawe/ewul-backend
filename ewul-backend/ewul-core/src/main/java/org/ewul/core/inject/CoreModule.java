package org.ewul.core.inject;

import com.google.inject.AbstractModule;
import org.ewul.core.config.CoreConfiguration;
import org.ewul.core.entity.EntityDataHandler;

import java.util.Objects;

public class CoreModule extends AbstractModule {

    protected final CoreConfiguration configuration;
    protected final EntityDataHandler handler;

    public CoreModule(CoreConfiguration configuration, EntityDataHandler handler) {
        this.configuration = Objects.requireNonNull(configuration);
        this.handler = Objects.requireNonNull(handler);
    }

    @Override
    protected void configure() {
        bind(CoreConfiguration.class).toInstance(configuration);
        bind(EntityDataHandler.class).toInstance(handler);
    }

}
