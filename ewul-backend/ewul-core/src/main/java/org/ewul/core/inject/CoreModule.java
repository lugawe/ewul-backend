package org.ewul.core.inject;

import com.google.inject.AbstractModule;
import org.ewul.core.config.CoreConfiguration;
import org.ewul.core.entity.TransactionHandler;

import java.util.Objects;

public class CoreModule extends AbstractModule {

    private final CoreConfiguration configuration;
    private final TransactionHandler handler;

    public CoreModule(CoreConfiguration configuration, TransactionHandler handler) {
        this.configuration = Objects.requireNonNull(configuration);
        this.handler = Objects.requireNonNull(handler);
    }

    @Override
    protected void configure() {
        bind(CoreConfiguration.class).toInstance(configuration);
        bind(TransactionHandler.class).toInstance(handler);
    }

}
