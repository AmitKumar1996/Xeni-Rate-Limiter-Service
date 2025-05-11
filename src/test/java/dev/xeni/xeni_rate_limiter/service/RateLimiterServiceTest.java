package dev.xeni.xeni_rate_limiter.service;

import dev.xeni.xeni_rate_limiter.model.ClientConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class RateLimiterServiceTest {

    @InjectMocks
    private RateLimiterService rateLimiterService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); 
    }

    @Test
    public void testRegisterClient() {
        ClientConfig config = new ClientConfig();
        config.setClientId("testClient");
        config.setRequestsPerWindow(100);

        rateLimiterService.registerClient(config);

        assertNotNull(rateLimiterService.getClientConfig("testClient"));
        assertEquals(100, rateLimiterService.getClientConfig("testClient").getRequestsPerWindow());
    }

    @Test
    public void testIsRequestAllowed() {
        ClientConfig config = new ClientConfig();
        config.setClientId("testClient");
        config.setRequestsPerWindow(3);
        rateLimiterService.registerClient(config);

        long timestamp = System.currentTimeMillis() / 1000;
        assertTrue(rateLimiterService.isRequestAllowed("testClient", timestamp));
    }

    @Test
    public void testIsRequestNotAllowed() {
        ClientConfig config = new ClientConfig();
        config.setClientId("testClient");
        config.setRequestsPerWindow(0);  
        rateLimiterService.registerClient(config);

        long timestamp = System.currentTimeMillis() / 1000;
        assertFalse(rateLimiterService.isRequestAllowed("testClient", timestamp));
    }

    @Test
    public void testGetClientConfig() {
        ClientConfig config = new ClientConfig();
        config.setClientId("testClient");
        config.setRequestsPerWindow(100);
        rateLimiterService.registerClient(config);

        ClientConfig result = rateLimiterService.getClientConfig("testClient");
        assertNotNull(result);
        assertEquals("testClient", result.getClientId());
        assertEquals(100, result.getRequestsPerWindow());
    }
}
