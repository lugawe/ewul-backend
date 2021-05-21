package org.ewul.core.config;

import java.io.Serializable;

public class CoreConfiguration implements Serializable {

    private String name = "ewul-core-configuration";

    public CoreConfiguration() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CoreConfiguration empty() {
        return new CoreConfiguration();
    }

}
