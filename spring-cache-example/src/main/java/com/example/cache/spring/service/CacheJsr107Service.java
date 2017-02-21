package com.example.cache.spring.service;

import org.springframework.stereotype.Service;

import javax.cache.annotation.*;

@Service
public class CacheJsr107Service {

    @CacheResult(cacheName = "jsr107Cache")
    public String get(String str) {
        heavyTask();
        return "Arg:" + str;
    }

    @CachePut(cacheName = "jsr107Cache")
    public String put(String str, @CacheValue String newStr) {
        heavyTask();
        return "Arg:" + newStr;
    }

    @CacheRemove(cacheName = "jsr107Cache")
    public void remove(String str) {
    }

    @CacheRemoveAll(cacheName = "jsr107Cache")
    public void removeAll() {
    }

    private void heavyTask() {
        try {
            Thread.sleep(2_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
