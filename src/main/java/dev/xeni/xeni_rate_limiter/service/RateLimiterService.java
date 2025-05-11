package dev.xeni.xeni_rate_limiter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.xeni.xeni_rate_limiter.model.ClientConfig;
import dev.xeni.xeni_rate_limiter.service.strategy.FixedWindowRateLimiter;
import dev.xeni.xeni_rate_limiter.store.DatabaseStore;

@Service
public class RateLimiterService {

    private final DatabaseStore databaseStore;
    private final FixedWindowRateLimiter fixedWindowRateLimiter;

    @Autowired
    public RateLimiterService(DatabaseStore databaseStore, FixedWindowRateLimiter fixedWindowRateLimiter) {
        this.databaseStore = databaseStore;
        this.fixedWindowRateLimiter = fixedWindowRateLimiter;
    }

    public boolean isRequestAllowed(String clientId, long timestamp) {
        ClientConfig clientConfig = databaseStore.getClientConfig(clientId);
        if (clientConfig == null) {
            throw new IllegalArgumentException("Client not registered");
        }
        return fixedWindowRateLimiter.isRequestAllowed(clientConfig, timestamp);
    }

    public void registerClient(ClientConfig config) {
        databaseStore.saveClientConfig(config);
    }

    public ClientConfig getClientConfig(String clientId) {
        return databaseStore.getClientConfig(clientId);
    }

	
}
