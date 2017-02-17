package com.example.cache.spring.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Cacheable("myCache")
    public String getString() {
        heavyTask();
        return "Hello!!";
    }

    @Cacheable("myCache")
    public String getAnotherString() {
        heavyTask();
        return "Another!!";
    }

    private void heavyTask() {
        try {
            Thread.sleep(2_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
