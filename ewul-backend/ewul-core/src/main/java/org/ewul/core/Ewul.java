package org.ewul.core;

import com.google.common.collect.ObjectArrays;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.ewul.core.config.CoreConfiguration;
import org.ewul.core.entity.EntityManagerHandler;
import org.ewul.core.inject.CoreModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Ewul {

    private static final Logger log = LoggerFactory.getLogger(Ewul.class);

    private Ewul() {
    }

    public static void init() {
        log.info("init");
    }

    public static Injector injector(CoreConfiguration configuration,
                                    EntityManagerHandler handler,
                                    Module... modules) {
        log.info("create injector");

        Module coreModule = new CoreModule(configuration, handler);
        return Guice.createInjector(ObjectArrays.concat(coreModule, modules));
    }

}
