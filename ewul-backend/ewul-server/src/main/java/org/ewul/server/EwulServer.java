package org.ewul.server;

import com.google.common.eventbus.EventBus;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.ewul.core.util.Lazy;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class EwulServer extends Application<EwulServerConfig> {

    public static final Lazy<EwulServer> INSTANCE = Lazy.of(EwulServer::new);
    public static final Lazy<EventBus> EVENT_BUS = Lazy.of(() -> new EventBus("ewul-server-main"));

    public static EventBus events() {
        return EVENT_BUS.get();
    }

    public static void main(String[] args) throws Exception {
        INSTANCE.get().run(args);
    }

    public EwulServer() {
    }

    @Override
    public void initialize(Bootstrap<EwulServerConfig> bootstrap) {
        bootstrap.addBundle(EwulServerModule.HIBERNATE_BUNDLE.get());
        bootstrap.addBundle(GuiceBundle.builder().enableAutoConfig("org.ewul.server").modules(EwulServerModule.INSTANCE.get()).build());
    }

    @Override
    public void run(EwulServerConfig config, Environment environment) {
        environment.jersey().setUrlPattern("/api/*");
    }

}
