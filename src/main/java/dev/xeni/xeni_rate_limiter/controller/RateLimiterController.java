package dev.xeni.xeni_rate_limiter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dev.xeni.xeni_rate_limiter.model.ClientConfig;
import dev.xeni.xeni_rate_limiter.service.RateLimiterService;

@RestController
@RequestMapping("/api")
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;

    @Autowired
    public RateLimiterController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    // Endpoint to register a client with ClientConfig object
    @PostMapping("/register")
    public String registerClient(@RequestBody ClientConfig clientConfig) {
        rateLimiterService.registerClient(clientConfig);
        return "Client registered successfully.";
    }

    // Endpoint to check if request is allowed
    @GetMapping("/request")
    public String isRequestAllowed(
            @RequestParam String clientId,
            @RequestParam(required = false) Long timestamp) {

        long currentTimestamp = (timestamp != null) ? timestamp : System.currentTimeMillis() / 1000;
        boolean allowed = rateLimiterService.isRequestAllowed(clientId, currentTimestamp);

        return allowed ? "Request allowed" : "Rate limit exceeded";
    }

   
    @GetMapping("/client-config")
    public ClientConfig getClientConfig(@RequestParam String clientId) {
        return rateLimiterService.getClientConfig(clientId);
    }
}
