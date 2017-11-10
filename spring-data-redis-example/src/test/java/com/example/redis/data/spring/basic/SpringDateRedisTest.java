package com.example.redis.data.spring.basic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataRedisConfig.class)
public class SpringDateRedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void string() {
        stringRedisTemplate.delete("my_key");
        stringRedisTemplate.opsForValue().set("my_key", "my_val");
        System.out.println(stringRedisTemplate.opsForValue().get("my_key"));
    }

    @Test
    public void list() {
        stringRedisTemplate.delete("my_list");
        stringRedisTemplate.opsForList().rightPush("my_list", "aaa");
        stringRedisTemplate.opsForList().rightPush("my_list", "bbb");
        stringRedisTemplate.opsForList().rightPush("my_list", "ccc");
        System.out.println(stringRedisTemplate.opsForList().range("my_list", 0, -1));
    }

    @Test
    public void set() {
        stringRedisTemplate.delete("my_set");
        stringRedisTemplate.opsForSet().add("my_set", "AAA");
        stringRedisTemplate.opsForSet().add("my_set", "BBB");
        stringRedisTemplate.opsForSet().add("my_set", "CCC");
        stringRedisTemplate.opsForSet().add("my_set", "AAA");
        System.out.println(stringRedisTemplate.opsForSet().members("my_set"));
    }

    @Test
    public void hash() {
        stringRedisTemplate.delete("my_hash");
        stringRedisTemplate.opsForHash().put("my_hash", "aaa", "111");
        stringRedisTemplate.opsForHash().put("my_hash", "bbb", "222");
        stringRedisTemplate.opsForHash().put("my_hash", "ccc", "333");
        System.out.println(stringRedisTemplate.opsForHash().get("my_hash", "bbb"));
    }

    @Test
    public void zset() {
        stringRedisTemplate.delete("my_zset");
        stringRedisTemplate.opsForZSet().add("my_zset", "member1", 111);
        stringRedisTemplate.opsForZSet().add("my_zset", "member2", 222);
        stringRedisTemplate.opsForZSet().add("my_zset", "member3", 333);
        System.out.println(stringRedisTemplate.opsForZSet().rangeByScore("my_zset", 100, 300));
    }
}