package com.example.redis.data.spring.cache;

import com.example.redis.data.spring.serialize.SpringDateRedisSerializeTest.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataRedisCacheConfig.class)
public class SpringDateRedisCacheTest {
    @Autowired
    @Qualifier("redisTemplateJacksonSerialize")
    private RedisTemplate<String, User> redisTemplateJacksonSerialize;

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
}
