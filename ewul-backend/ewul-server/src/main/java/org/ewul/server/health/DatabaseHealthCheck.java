package org.ewul.server.health;

import ru.vyarus.dropwizard.guice.module.installer.feature.health.NamedHealthCheck;

public class DatabaseHealthCheck extends NamedHealthCheck {

    public DatabaseHealthCheck() {
    }

    @Override
    public Result check() throws Exception {
        return Result.healthy();
    }

    @Override
    public String getName() {
        return "database";
    }

}
