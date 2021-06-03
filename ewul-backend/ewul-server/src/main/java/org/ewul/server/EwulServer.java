package org.ewul.server;

import org.ewul.core.Ewul;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("org.ewul.model.db")
public class EwulServer {

    private static final Logger log = LoggerFactory.getLogger(EwulServer.class);

    public static void main(String[] args) {
        log.info("start ewul server");
        Ewul.init();
        SpringApplication.run(EwulServer.class, args);
    }

}
