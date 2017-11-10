package com.example.redis.data.spring.serialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDateRedisSerializeConfig.class)
public class SpringDateRedisSerializeTest {
    @Autowired
    @Qualifier("redisTemplateDefault")
    private RedisTemplate<String, User> redisTemplateDefault;
    @Autowired
    @Qualifier("redisTemplateStringSerialize")
    private RedisTemplate<String, String> redisTemplateStringSerialize;
    @Autowired
    @Qualifier("redisTemplateJacksonSerialize")
    private RedisTemplate<String, User> redisTemplateJacksonSerialize;

    @Test
    public void defaultSerialize() {
        System.out.println(redisTemplateDefault.getConnectionFactory());
        System.out.println(redisTemplateDefault.getKeySerializer().getClass().getName());
        System.out.println(redisTemplateDefault.getValueSerializer().getClass().getName());

        User alice = new User("alice", 20);
        redisTemplateDefault.delete("alice");
        redisTemplateDefault.opsForValue().set("alice", alice);

        User deserializedAlice = redisTemplateDefault.opsForValue().get("alice");

        assertThat(deserializedAlice).isEqualTo(alice);
    }

    @Test
    public void stringSerialize() {
        System.out.println(redisTemplateStringSerialize.getConnectionFactory());
        System.out.println(redisTemplateStringSerialize.getKeySerializer().getClass().getName());
        System.out.println(redisTemplateStringSerialize.getValueSerializer().getClass().getName());

        User bob = new User("bob", 33);
        redisTemplateStringSerialize.delete("bob");
        redisTemplateStringSerialize.opsForValue().set("bob", bob.toString());

        String deserializedBob = redisTemplateStringSerialize.opsForValue().get("bob");

        assertThat(deserializedBob).isEqualTo(bob.toString());
    }

    @Test
    public void jacksonSerialize() {
        System.out.println(redisTemplateJacksonSerialize.getConnectionFactory());
        System.out.println(redisTemplateJacksonSerialize.getKeySerializer().getClass().getName());
        System.out.println(redisTemplateJacksonSerialize.getValueSerializer().getClass().getName());

        User cindy = new User("cindy", 44);
        redisTemplateJacksonSerialize.delete("cindy");
        redisTemplateJacksonSerialize.opsForValue().set("cindy", cindy);

        User deserializedCindy = redisTemplateJacksonSerialize.opsForValue().get("cindy");

        assertThat(deserializedCindy).isEqualTo(cindy);
    }

    @Data
    @AllArgsConstructor
    public static class User implements Serializable {
        private String name;
        private int age;
    }
}
