package org.ewul.server;

import org.ewul.core.config.CoreConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class EwulServerConfig {

    private CoreConfiguration coreConfiguration = new CoreConfiguration();

    public EwulServerConfig() {
    }

    public CoreConfiguration getCoreConfiguration() {
        return coreConfiguration;
    }

    public void setCoreConfiguration(CoreConfiguration coreConfiguration) {
        this.coreConfiguration = coreConfiguration;
    }

}
