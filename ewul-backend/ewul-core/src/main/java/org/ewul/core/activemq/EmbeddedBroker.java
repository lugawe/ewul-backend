package org.ewul.core.activemq;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.SslBrokerService;
import org.ewul.model.config.ActiveMQConfiguration;
import org.ewul.model.config.CoreConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import java.net.URI;
import java.security.SecureRandom;
import java.util.Objects;

@Singleton
public class EmbeddedBroker {

    private static final Logger log = LoggerFactory.getLogger(EmbeddedBroker.class);

    protected final ActiveMQConfiguration activeMQConfiguration;

    private BrokerService brokerService;

    public EmbeddedBroker(ActiveMQConfiguration activeMQConfiguration) {
        this.activeMQConfiguration = Objects.requireNonNull(activeMQConfiguration);
    }

    @Inject
    public EmbeddedBroker(CoreConfiguration coreConfiguration) {
        this(coreConfiguration.getActiveMQConfiguration());
    }

    protected BrokerService createSecureBrokerService(KeyManager[] keyManagers,
                                                      TrustManager[] trustManagers) throws Exception {
        log.debug("create secure broker service");
        SslBrokerService brokerService = new SslBrokerService();
        brokerService.addSslConnector(new URI(activeMQConfiguration.getBrokerUrl()),
                keyManagers, trustManagers, new SecureRandom());
        return brokerService;
    }

    protected void configureBrokerService(BrokerService brokerService) {
        log.debug("configure broker service");
        brokerService.setPersistent(false);
    }

    public synchronized void start(KeyManager[] keyManagers, TrustManager[] trustManagers) throws Exception {
        if (brokerService == null) {
            brokerService = createSecureBrokerService(keyManagers, trustManagers);
            configureBrokerService(brokerService);
        }
        brokerService.start();
    }

    public synchronized void stop() throws Exception {
        if (brokerService != null) {
            brokerService.stop();
        }
    }

}
