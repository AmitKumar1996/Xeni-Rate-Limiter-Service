package dev.xeni.xeni_rate_limiter.store;



import dev.xeni.xeni_rate_limiter.model.ClientConfig;
import dev.xeni.xeni_rate_limiter.repository.ClientConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseStore {

    @Autowired
    private ClientConfigRepository repository;

    public void saveClientConfig(ClientConfig config) {
        repository.save(config);
    }

    public ClientConfig getClientConfig(String clientId) {
        return repository.findById(clientId).orElse(null);
    }
}
