package com.example.retry.spring.springretryexample.service;

import com.example.retry.spring.springretryexample.service.MyService.User;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MyServiceWithRetryTemplate {
    public void execute() throws Throwable {
        RetryTemplate template = new RetryTemplate();

        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
            System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
            throw new RuntimeException();
        });

        System.out.println(user);
    }

    public void executeWithRetryContext() throws Throwable {
        RetryTemplate template = new RetryTemplate();

        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
            System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
            throw new RuntimeException();
        });

        System.out.println(user);
    }

    public void executeWithRetryPolicy() throws Throwable {
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy policy = new SimpleRetryPolicy(5);

        template.setRetryPolicy(policy);

        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
            System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
            throw new RuntimeException();
        });

        System.out.println(user);
    }

    public void executeWithBackoffPolicy() throws Throwable {
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy policy = new SimpleRetryPolicy(5);

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(2_000L);

        template.setRetryPolicy(policy);
        template.setBackOffPolicy(backOffPolicy);

        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
            System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
            throw new RuntimeException();
        });

        System.out.println(user);
    }

    public void executeWithMaxInternal() throws Throwable {
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy policy = new SimpleRetryPolicy(5);

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(2_000L);
        backOffPolicy.setMaxInterval(4_000L);

        template.setRetryPolicy(policy);
        template.setBackOffPolicy(backOffPolicy);

        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
            System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
            throw new RuntimeException();
        });

        System.out.println(user);
    }

    public void executeWithRecovery() throws Throwable {
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy policy = new SimpleRetryPolicy(5);

//        TimeoutRetryPolicy policy = new TimeoutRetryPolicy();
//        policy.setTimeout(1_000L);

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1_000L);

        template.setRetryPolicy(policy);
        template.setBackOffPolicy(backOffPolicy);

        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
                    System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
                    throw new RuntimeException();
                },
                retryContext -> {
                    System.out.println("retry!");
                    return new User("Cindy", 24);
                });

        System.out.println(user);
    }

    public User getUser() {
        return new User("Bobby", 20);
    }
}
