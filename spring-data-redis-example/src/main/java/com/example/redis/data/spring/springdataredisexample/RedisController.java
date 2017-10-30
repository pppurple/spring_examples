package com.example.redis.data.spring.springdataredisexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {
    @Autowired
    private RedisService redisService;

    @RequestMapping("/redis/strings")
    public String getStrings() {
        redisService.string();
        return "ok";
    }
}
