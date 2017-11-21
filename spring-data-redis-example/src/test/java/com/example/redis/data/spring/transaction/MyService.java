package com.example.redis.data.spring.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional(transactionManager = "transactionManager")
public class MyService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Transactional
    public void addAndIncrement(String key, String value, String counterName) throws InterruptedException {
//        redisTemplate.multi();

        redisTemplate.opsForSet().add(key, value);
        redisTemplate.opsForValue().increment(counterName, 1L);
        redisTemplate.opsForValue().increment(counterName, 1L);

        Thread.sleep(10_000L);

        redisTemplate.opsForValue().increment(counterName, 1L);

//        redisTemplate.exec();
    }
}
