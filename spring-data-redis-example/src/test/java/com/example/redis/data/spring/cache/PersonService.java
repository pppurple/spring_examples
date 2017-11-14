package com.example.redis.data.spring.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Random;

@Service
public class PersonService {

    @Cacheable(cacheNames = "PersonCache", key = "'Person:' + #name")
    public Person createPerson(String name) throws InterruptedException {
        Thread.sleep(3_000L);
        return new Person(name);
    }

    @Cacheable(cacheManager = "ExpireManager", cacheNames = "ExpirePersonCache", key = "'ExpirePerson:' + #name")
    public Person createPersonWithExpire(String name) throws InterruptedException {
        Thread.sleep(3_000L);
        return new Person(name);
    }

    @CacheEvict(cacheNames = {"PersonCache", "ExpirePersonCache"})
    public void evict(String key) {
    }

    // all args constructorがないとjsonからデシリアライズ出来ない
    @AllArgsConstructor
    public static class Person implements Serializable {
        @Getter
        private String name;
        @Getter
        private int randomValue;

        private static Random random = new Random();

        Person(String name) {
            this.name = name;
            this.randomValue = random.nextInt();
        }
    }
}
