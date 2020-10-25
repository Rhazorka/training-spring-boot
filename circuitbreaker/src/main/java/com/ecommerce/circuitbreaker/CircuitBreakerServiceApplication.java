package com.ecommerce.circuitbreaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
// we gonna use a hystrix circuit breaker
public class CircuitBreakerServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(CircuitBreakerServiceApplication.class, args);
	}
}
