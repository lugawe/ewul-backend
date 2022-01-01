package org.ewul.server;

import org.ewul.core.modules.auth.BCryptPasswordHashing;
import org.ewul.core.modules.auth.JwtTokenHandler;
import org.ewul.core.modules.auth.PasswordHashing;
import org.ewul.core.modules.auth.TokenHandler;
import org.ewul.model.config.CoreConfiguration;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

public class EwulServerModule extends DropwizardAwareModule<EwulServerConfig> {

    public EwulServerModule() {
    }

    @Override
    public void configure() {
        bind(CoreConfiguration.class).toProvider(() -> configuration().getCoreConfiguration());
        bind(PasswordHashing.class).to(BCryptPasswordHashing.class);
        bind(TokenHandler.class).to(JwtTokenHandler.class);
    }

}
