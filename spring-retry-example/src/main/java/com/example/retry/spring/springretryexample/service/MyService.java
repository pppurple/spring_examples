package com.example.retry.spring.springretryexample.service;

import lombok.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MyService {
    private int retryCount = 0;

    @Retryable
    public void retryable() {
        System.out.println(LocalDateTime.now() + " retry=" + retryCount);
        retryCount++;

        throw new RuntimeException();
    }

    @Retryable(maxAttempts = 5)
    public void retryableWithRetryPolicy() {
        System.out.println(LocalDateTime.now() + " retry=" + retryCount);
        retryCount++;

        throw new RuntimeException();
    }

    @Retryable(backoff = @Backoff(delay = 2_000))
    public void retryableWithBackoffPolicy() {
        System.out.println(LocalDateTime.now() + " retry=" + retryCount);
        retryCount++;

        throw new RuntimeException();
    }

    @Retryable(backoff = @Backoff(delay = 2_000, maxDelay = 4_000))
    public void retryableWithMaxDelay() {
        System.out.println(LocalDateTime.now() + " retry=" + retryCount);
        retryCount++;

        throw new RuntimeException();
    }

    @Retryable(UserNotFoundException.class)
    public void retryableWithException(String name) throws UserNotFoundException {
        System.out.println(LocalDateTime.now() + " retry=" + retryCount + ", name=" + name);
        retryCount++;

        throw new UserNotFoundException();
    }

    @Recover
    public void recover(UserNotFoundException e, String name) {
        e.printStackTrace();
        System.out.println("recovered! name=" + name);
    }

    @Value
    public static class User {
        private String name;
        private int age;
    }

    public static class UserNotFoundException extends Exception {
    }

    public static class UnexpectedException extends Exception {
    }
}
