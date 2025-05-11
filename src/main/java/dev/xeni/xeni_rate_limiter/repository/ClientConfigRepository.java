package dev.xeni.xeni_rate_limiter.repository;



import dev.xeni.xeni_rate_limiter.model.ClientConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientConfigRepository extends JpaRepository<ClientConfig, String> {
}
