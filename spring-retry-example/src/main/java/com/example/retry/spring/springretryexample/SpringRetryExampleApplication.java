package com.example.retry.spring.springretryexample;

import com.example.retry.spring.springretryexample.service.MyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class SpringRetryExampleApplication implements CommandLineRunner {
    private final MyService myService;

    public static void main(String[] args) {
        SpringApplication.run(SpringRetryExampleApplication.class, args);
    }

    public SpringRetryExampleApplication(MyService myService) {
        this.myService = myService;
    }

    @Override
    public void run(String... args) {
        try {
            myService.retryable();
            myService.retryableWithRetryPolicy();
            myService.retryableWithBackoffPolicy();
            myService.retryableWithMaxDelay();
            myService.retryableWithException("test");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

