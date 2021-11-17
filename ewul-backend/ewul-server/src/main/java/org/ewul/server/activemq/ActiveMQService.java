package org.ewul.server.activemq;

import io.dropwizard.lifecycle.Managed;
import org.ewul.core.activemq.EmbeddedBroker;

import javax.inject.Inject;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import java.util.Objects;

public class ActiveMQService implements Managed {

    private final EmbeddedBroker embeddedBroker;

    @Inject
    public ActiveMQService(EmbeddedBroker embeddedBroker) {
        this.embeddedBroker = Objects.requireNonNull(embeddedBroker);
    }

    @Override
    public void start() throws Exception {
        embeddedBroker.start(new KeyManager[0], new TrustManager[0]);
    }

    @Override
    public void stop() throws Exception {
        embeddedBroker.stop();
    }

}
