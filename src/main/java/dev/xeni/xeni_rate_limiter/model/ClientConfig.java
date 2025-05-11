package dev.xeni.xeni_rate_limiter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ClientConfig {

    @Id
    private String clientId;         // Unique key for the client 
    private int requestsPerWindow;   // e.g., 1000 requests
    private long windowInSeconds;    // e.g., 60 seconds

  
    public ClientConfig() {
        
    }


    public ClientConfig(String clientId, int requestsPerWindow, long windowInSeconds) {
        this.clientId = clientId;
        this.requestsPerWindow = requestsPerWindow;
        this.windowInSeconds = windowInSeconds;
    }

  
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getRequestsPerWindow() {
        return requestsPerWindow;
    }

    public void setRequestsPerWindow(int requestsPerWindow) {
        this.requestsPerWindow = requestsPerWindow;
    }

    public long getWindowInSeconds() {
        return windowInSeconds;
    }

    public void setWindowInSeconds(long windowInSeconds) {
        this.windowInSeconds = windowInSeconds;
    }

    
    @Override
    public String toString() {
        return "ClientConfig [clientId=" + clientId + ", requestsPerWindow=" + requestsPerWindow +
                ", windowInSeconds=" + windowInSeconds + "]";
    }
}
