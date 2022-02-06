package org.ewul.server;

import org.ewul.model.config.CoreConfiguration;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

public class EwulServerModule extends DropwizardAwareModule<EwulServerConfig> {

    public EwulServerModule() {
    }

    @Override
    public void configure() {
        bind(CoreConfiguration.class).toProvider(() -> configuration().getCoreConfiguration());
    }

}
