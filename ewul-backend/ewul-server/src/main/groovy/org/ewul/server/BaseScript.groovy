package org.ewul.server

import groovy.util.logging.Slf4j

@Slf4j
abstract class BaseScript<T> extends Script {

    protected BaseScript() {
        log.debug("new script created")
    }

    @Override
    Object run() {
        log.debug("calling execute")
        return execute()
    }

    abstract T execute() throws Exception

}
