package org.ewul.server;

import org.ewul.core.Ewul;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EntityScan("org.ewul.model.db")
public class EwulServer implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(EwulServer.class);

    public static void main(String[] args) {
        SpringApplication.run(EwulServer.class, args);
    }

    public EwulServer() {
        log.debug("ewul-server init");
    }

    @Override
    public void setEnvironment(Environment environment) {
        Ewul.init(environment.getActiveProfiles());
    }

}
