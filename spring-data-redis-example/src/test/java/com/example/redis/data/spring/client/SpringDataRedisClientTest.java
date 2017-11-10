package com.example.redis.data.spring.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataRedisClientConfig.class)
public class SpringDataRedisClientTest {
    @Autowired
    @Qualifier("jedisRedisTemplate")
    private StringRedisTemplate jedisRedisTemplate;
    @Autowired
    @Qualifier("lettuceRedisTemplate")
    private StringRedisTemplate lettuceRedisTemplate;

    @Test
    public void jedisTest() {
        System.out.println(jedisRedisTemplate.getConnectionFactory());

        jedisRedisTemplate.delete("jedis");
        jedisRedisTemplate.opsForValue().set("jedis", "jedisTemplate");
        String got = jedisRedisTemplate.opsForValue().get("jedis");
        assertThat(got).isEqualTo("jedisTemplate");
    }

    @Test
    public void lettuceTest() {
        System.out.println(lettuceRedisTemplate.getConnectionFactory());

        lettuceRedisTemplate.delete("jedis");
        lettuceRedisTemplate.opsForValue().set("jedis", "jedisTemplate");
        String got = lettuceRedisTemplate.opsForValue().get("jedis");
        assertThat(got).isEqualTo("jedisTemplate");
    }
}
