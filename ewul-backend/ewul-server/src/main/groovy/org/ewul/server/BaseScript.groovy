package org.ewul.server

import groovy.util.logging.Slf4j

@Slf4j
abstract class BaseScript<T> extends Script {

    @Override
    Object run() {
        log.debug("calling execute")
        return execute()
    }

    abstract T execute()

}
