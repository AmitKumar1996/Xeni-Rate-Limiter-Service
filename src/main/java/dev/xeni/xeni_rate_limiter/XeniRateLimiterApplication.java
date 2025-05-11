package dev.xeni.xeni_rate_limiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XeniRateLimiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(XeniRateLimiterApplication.class, args);
		
		System.out.println("Working...");
	}

}
