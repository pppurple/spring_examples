package com.example.cache.spring.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    @Cacheable("cacheWithArg")
    public String getStringWithArg(String str) {
        heavyTask();
        return "Arg:" + str;
    }

    @Cacheable("cacheWithArgs")
    public String getStringWithArgs(String str, int num, boolean isActive) {
        heavyTask();
        return "Args:" + str + num + isActive;
    }

    @Cacheable(value = "cacheWithArgsAndKey", key = "#str")
    public String getStringWithArgsAndKey(String str, int num, boolean isActive) {
        heavyTask();
        return "Args:" + str + num + isActive;
    }

    @CachePut(cacheNames = "cacheWithArg", key = "#str")
    public String put(String str, String newStr) {
        heavyTask();
        return "Arg:" + newStr;
    }

    @CacheEvict(cacheNames = "cacheWithArg")
    public void evict(String str) {
    }

    @CacheEvict(cacheNames = "cacheWithArg", allEntries = true)
    public void evictAll(String str) {
    }

    @Cacheable(value = "anotherCacheWithArg")
    public String getAnotherStringWithArg(String name) {
        heavyTask();
        return "Name:" + name;
    }

    @Caching(evict = {@CacheEvict("cacheWithArg"), @CacheEvict(cacheNames = "anotherCacheWithArg")})
    public void caching(String str) {
    }

    private void heavyTask() {
        try {
            Thread.sleep(2_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
