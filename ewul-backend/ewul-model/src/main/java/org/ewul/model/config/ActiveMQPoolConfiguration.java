package org.ewul.model.config;

import javax.validation.Valid;
import java.io.Serializable;

public class ActiveMQPoolConfiguration implements Serializable {

    @Valid
    private Integer maxConnections;

    @Valid
    private Integer connectionTimeout;

    @Valid
    private Boolean createConnectionOnStartup;

    @Valid
    private Boolean blockIfSessionPoolIsFull;

    @Valid
    private Long blockIfSessionPoolIsFullTimeout;

    @Valid
    private Long expiryTimeout;

    @Valid
    private Integer idleTimeout;

    @Valid
    private Integer maximumActiveSessionPerConnection;

    @Valid
    private Boolean reconnectOnException;

    @Valid
    private Long timeBetweenExpirationCheckMillis;

    @Valid
    private Boolean useAnonymousProducers;

    public ActiveMQPoolConfiguration() {
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Boolean getCreateConnectionOnStartup() {
        return createConnectionOnStartup;
    }

    public void setCreateConnectionOnStartup(Boolean createConnectionOnStartup) {
        this.createConnectionOnStartup = createConnectionOnStartup;
    }

    public Boolean getBlockIfSessionPoolIsFull() {
        return blockIfSessionPoolIsFull;
    }

    public void setBlockIfSessionPoolIsFull(Boolean blockIfSessionPoolIsFull) {
        this.blockIfSessionPoolIsFull = blockIfSessionPoolIsFull;
    }

    public Long getBlockIfSessionPoolIsFullTimeout() {
        return blockIfSessionPoolIsFullTimeout;
    }

    public void setBlockIfSessionPoolIsFullTimeout(Long blockIfSessionPoolIsFullTimeout) {
        this.blockIfSessionPoolIsFullTimeout = blockIfSessionPoolIsFullTimeout;
    }

    public Long getExpiryTimeout() {
        return expiryTimeout;
    }

    public void setExpiryTimeout(Long expiryTimeout) {
        this.expiryTimeout = expiryTimeout;
    }

    public Integer getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Integer idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public Integer getMaximumActiveSessionPerConnection() {
        return maximumActiveSessionPerConnection;
    }

    public void setMaximumActiveSessionPerConnection(Integer maximumActiveSessionPerConnection) {
        this.maximumActiveSessionPerConnection = maximumActiveSessionPerConnection;
    }

    public Boolean getReconnectOnException() {
        return reconnectOnException;
    }

    public void setReconnectOnException(Boolean reconnectOnException) {
        this.reconnectOnException = reconnectOnException;
    }

    public Long getTimeBetweenExpirationCheckMillis() {
        return timeBetweenExpirationCheckMillis;
    }

    public void setTimeBetweenExpirationCheckMillis(Long timeBetweenExpirationCheckMillis) {
        this.timeBetweenExpirationCheckMillis = timeBetweenExpirationCheckMillis;
    }

    public Boolean getUseAnonymousProducers() {
        return useAnonymousProducers;
    }

    public void setUseAnonymousProducers(Boolean useAnonymousProducers) {
        this.useAnonymousProducers = useAnonymousProducers;
    }

}
