package dev.xeni.xeni_rate_limiter.service;

import dev.xeni.xeni_rate_limiter.model.ClientConfig;
import dev.xeni.xeni_rate_limiter.service.strategy.FixedWindowRateLimiter;
import dev.xeni.xeni_rate_limiter.store.DatabaseStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RateLimiterServiceTest {

    @Mock
    private DatabaseStore databaseStore;

    @Mock
    private FixedWindowRateLimiter fixedWindowRateLimiter;

    @InjectMocks
    private RateLimiterService rateLimiterService;

    private ClientConfig clientConfig;

    @Before
    public void setUp() {
        clientConfig = new ClientConfig();
        clientConfig.setClientId("test-client");
        clientConfig.setRequestsPerWindow(10);
        clientConfig.setWindowInSeconds(60);
    }

    @Test
    public void testRegisterClient_shouldStoreConfig() {
        rateLimiterService.registerClient(clientConfig);
        verify(databaseStore, times(1)).saveClientConfig(clientConfig);
    }

    @Test
    public void testIsRequestAllowed_shouldReturnTrue() {
        long timestamp = System.currentTimeMillis() / 1000;
        when(databaseStore.getClientConfig("test-client")).thenReturn(clientConfig);
        when(fixedWindowRateLimiter.isRequestAllowed(clientConfig, timestamp)).thenReturn(true);

        boolean allowed = rateLimiterService.isRequestAllowed("test-client", timestamp);

        assertTrue(allowed);
        verify(fixedWindowRateLimiter).isRequestAllowed(clientConfig, timestamp);
    }

    @Test
    public void testIsRequestAllowed_shouldReturnFalse() {
        long timestamp = System.currentTimeMillis() / 1000;
        when(databaseStore.getClientConfig("test-client")).thenReturn(clientConfig);
        when(fixedWindowRateLimiter.isRequestAllowed(clientConfig, timestamp)).thenReturn(false);

        boolean allowed = rateLimiterService.isRequestAllowed("test-client", timestamp);

        assertFalse(allowed);
        verify(fixedWindowRateLimiter).isRequestAllowed(clientConfig, timestamp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsRequestAllowed_clientNotFound_shouldThrow() {
        when(databaseStore.getClientConfig("unknown-client")).thenReturn(null);
        rateLimiterService.isRequestAllowed("unknown-client", System.currentTimeMillis() / 1000);
    }

    @Test
    public void testGetClientConfig_shouldReturnConfig() {
        when(databaseStore.getClientConfig("test-client")).thenReturn(clientConfig);
        ClientConfig result = rateLimiterService.getClientConfig("test-client");

        assertNotNull(result);
        assertEquals("test-client", result.getClientId());
        verify(databaseStore).getClientConfig("test-client");
    }

    @Test
    public void testGetClientConfig_notFound_shouldReturnNull() {
        when(databaseStore.getClientConfig("missing-client")).thenReturn(null);
        ClientConfig result = rateLimiterService.getClientConfig("missing-client");

        assertNull(result);
        verify(databaseStore).getClientConfig("missing-client");
    }
}
