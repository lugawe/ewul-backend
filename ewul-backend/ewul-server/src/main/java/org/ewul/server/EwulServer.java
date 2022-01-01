package org.ewul.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.ewul.core.util.Lazy;
import org.ewul.server.hibernate.HibernateBundle;
import org.ewul.server.hibernate.HibernateModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class EwulServer extends Application<EwulServerConfig> {

    private static final Logger log = LoggerFactory.getLogger(EwulServer.class);

    public static final Lazy<EwulServer> INSTANCE = Lazy.of(EwulServer::new);

    public static void main(String[] args) throws Exception {
        INSTANCE.get().run(args);
    }

    public EwulServer() {
    }

    @Override
    public void initialize(Bootstrap<EwulServerConfig> bootstrap) {
        bootstrap.addBundle(GuiceBundle.builder()
                .enableAutoConfig(getClass().getPackage().getName())
                .dropwizardBundles(HibernateBundle.getInstance())
                .modules(new EwulServerModule(), new HibernateModule(HibernateBundle.getInstance()))
                .build());
    }

    @Override
    public void run(EwulServerConfig config, Environment environment) {
        log.info("running version: {}", Constants.VERSION);
        environment.jersey().setUrlPattern(Constants.API_PATH);
    }

}
