package com.example.circuit.breaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@SpringBootApplication
public class CircuitBreakerExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CircuitBreakerExampleApplication.class, args);
	}
}
