package com.example.retry.spring.springretryexample;

import com.example.retry.spring.springretryexample.service.MyService;
import com.example.retry.spring.springretryexample.service.MyServiceWithRetryTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class SpringRetryExampleApplication implements CommandLineRunner {
    private final MyService myService;
    private final MyServiceWithRetryTemplate myServiceWithRetryTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SpringRetryExampleApplication.class, args);
    }

    public SpringRetryExampleApplication(MyService myService,
                                         MyServiceWithRetryTemplate myServiceWithRetryTemplate) {
        this.myService = myService;
        this.myServiceWithRetryTemplate = myServiceWithRetryTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            // annotation
            myService.retryable();
            myService.retryableWithRetryPolicy();
            myService.retryableWithBackoffPolicy();
            myService.retryableWithMaxDelay();
            myService.retryableWithException("test");

            // RetryTemplate
            myServiceWithRetryTemplate.execute();
            myServiceWithRetryTemplate.executeWithRetryContext();
            myServiceWithRetryTemplate.executeWithRetryPolicy();
            myServiceWithRetryTemplate.executeWithInitialInterval();
            myServiceWithRetryTemplate.executeWithMultiplier();
            myServiceWithRetryTemplate.executeWithMaxInternal();
            myServiceWithRetryTemplate.executeWithRecovery();
            myServiceWithRetryTemplate.executeWithRetryableExceptions();
            myServiceWithRetryTemplate.executeWithListeners();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

