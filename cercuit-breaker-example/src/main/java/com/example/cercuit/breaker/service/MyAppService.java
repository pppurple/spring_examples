package com.example.cercuit.breaker.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;

@Service
public class MyAppService {
    private final RestTemplate restTemplate;

    public MyAppService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @HystrixCommand(fallbackMethod = "fallbackText")
    public String getSupplierText() {
        URI uri = URI.create("http://localhost:8888/supplier/text");

        return restTemplate.getForObject(uri, String.class);
    }

    @HystrixCommand(
            fallbackMethod = "fallbackText",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5")
            }
    )
    public String getSupplierTextWithThreshold() {
        URI uri = URI.create("http://localhost:8888/supplier/text");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now + ": api called");
        return restTemplate.getForObject(uri, String.class);
    }

    @HystrixCommand(
            fallbackMethod = "fallbackText",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }
    )
    public String getSupplierTextWithSleepWindow() {
        URI uri = URI.create("http://localhost:8888/supplier/text");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now + ": api called");
        return restTemplate.getForObject(uri, String.class);
    }

    @HystrixCommand(
            fallbackMethod = "fallbackText",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "1")
            }
    )
    public String getSupplierTextWithPercentage() {
        URI uri = URI.create("http://localhost:8888/supplier/text");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now + ": api called");
        return restTemplate.getForObject(uri, String.class);
    }

    public String fallbackText() {
        return "Reply fallback text";
    }
}
