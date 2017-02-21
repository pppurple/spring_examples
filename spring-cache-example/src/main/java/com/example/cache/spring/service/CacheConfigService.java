package com.example.cache.spring.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "configCache")
public class CacheConfigService {
    @Cacheable
    public String get(String str) {
        heavyTask();
        return "get:" + str;
    }

    @CachePut
    public String put(String str) {
        heavyTask();
        return "put:" + str;
    }

    @CacheEvict
    public void delete(String str) {
    }

    private void heavyTask() {
        try {
            Thread.sleep(2_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
