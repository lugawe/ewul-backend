package org.ewul.server;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EwulServerConfig {

    private static final Logger log = LoggerFactory.getLogger(EwulServerConfig.class);

    @Autowired
    private SessionFactory sessionFactory;

    public EwulServerConfig() {
        log.info("init config");
    }

}
