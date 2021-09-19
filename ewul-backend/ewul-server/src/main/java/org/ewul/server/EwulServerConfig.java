package org.ewul.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.ewul.model.config.CoreConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class EwulServerConfig extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    @Valid
    @NotNull
    private CoreConfiguration coreConfiguration = new CoreConfiguration();

    public EwulServerConfig() {
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    @JsonProperty("core")
    public CoreConfiguration getCoreConfiguration() {
        return coreConfiguration;
    }

    @JsonProperty("core")
    public void setCoreConfiguration(CoreConfiguration coreConfiguration) {
        this.coreConfiguration = coreConfiguration;
    }

}
