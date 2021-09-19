package org.ewul.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.ewul.core.util.Lazy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class EwulServer extends Application<EwulServerConfig> {

    private static final Logger log = LoggerFactory.getLogger(EwulServer.class);

    private static final Lazy<EwulServer> instance = Lazy.of(EwulServer::new);

    public static void main(String[] args) throws Exception {
        instance.get().run(args);
    }

    public EwulServer() {
    }

    @Override
    public void initialize(Bootstrap<EwulServerConfig> bootstrap) {
        bootstrap.addBundle(GuiceBundle.builder().enableAutoConfig("org.ewul.server").build());
    }

    @Override
    public void run(EwulServerConfig config, Environment environment) {
    }

}
