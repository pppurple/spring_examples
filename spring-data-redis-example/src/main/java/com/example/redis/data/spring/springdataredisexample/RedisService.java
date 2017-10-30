package com.example.redis.data.spring.springdataredisexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
//    @Autowired
    private RedisTemplate redisTemplate;
    private StringRedisTemplate stringRedisTemplate;

    public RedisService(RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void string() {
        stringRedisTemplate.opsForValue().set("my_key", "my_val");
        System.out.println(stringRedisTemplate.opsForValue().get("my_key"));
    }

    public void list() {

    }
}
