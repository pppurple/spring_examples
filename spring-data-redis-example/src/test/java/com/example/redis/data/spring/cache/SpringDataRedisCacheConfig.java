package com.example.redis.data.spring.cache;

import com.example.redis.data.spring.cache.PersonService.Person;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
@ComponentScan("com.example.redis.data.spring.cache")
public class SpringDataRedisCacheConfig {
    /*
    @Bean
    public PersonService personService() {
        return new PersonService();
    }
    */

    @Primary
    @Bean
    public RedisCacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        return new RedisCacheManager(redisTemplate);
    }

    @Bean(name = "expireManager")
    public RedisCacheManager cacheManagerWithExpire(RedisTemplate<String, Object> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // set expire
        Map<String, Long> expireMap = new HashMap<>();
        expireMap.put("expirePersonCache", 10L);
        cacheManager.setExpires(expireMap);
        return cacheManager;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Person.class));
        return redisTemplate;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }
}
