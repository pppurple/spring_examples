package com.example.redis.data.spring.transaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataRedisTransactionConfig.class)
public class SpringDataRedisTransactionTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Before
    public void before() {
        redisTemplate.delete("my_key");
        redisTemplate.delete("my_set");
        redisTemplate.delete("counter");
    }

    @Test
    public void multiExecTest() throws InterruptedException {
        // transaction start
        redisTemplate.multi();

        redisTemplate.opsForValue().set("my_key", "my_value");
        redisTemplate.opsForSet().add("my_set", "aaa");
        redisTemplate.opsForValue().increment("counter", 1L);
        redisTemplate.opsForValue().increment("counter", 1L);
        Thread.sleep(10_000L);
        redisTemplate.opsForValue().increment("counter", 1L);

        List<Object> results = redisTemplate.exec();
        // transaction end

        System.out.println("-----------------");
        results.forEach(System.out::println);
        System.out.println("-----------------");

        System.out.println(redisTemplate.opsForSet().pop("my_set"));
        System.out.println(redisTemplate.opsForValue().get("counter"));
        /*
        > get "my_key"
        "my_val"
        > get "counter"
        "2"
         */
    }

    @Test
    public void discardTest() {
        // transaction start
        redisTemplate.multi();

        redisTemplate.opsForSet().add("my_set", "aaa");
        redisTemplate.opsForValue().increment("counter", 1L);
        redisTemplate.opsForValue().increment("counter", 1L);
        redisTemplate.opsForValue().increment("counter", 1L);

        redisTemplate.discard();
        // transaction end

        System.out.println(redisTemplate.opsForValue().get("my_key"));
        System.out.println(redisTemplate.opsForValue().get("counter"));
        /*
        > get "my_key"
        (nil)
        > get "counter"
        (nil)
         */
    }

    // https://www.javacodegeeks.com/2015/09/spring-data-and-redis.html#TransactionsRedis
    @Test
    public void useSessionCallbackTest() {
        System.out.println(redisTemplate.getConnectionFactory().getConvertPipelineAndTxResults());

        //execute a transaction
        List<Object> txResults = redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            @SuppressWarnings("unchecked")
            public <K, V> List<Object> execute(RedisOperations<K, V> operations) throws DataAccessException {
                operations.multi();

                operations.opsForSet().add((K)"my_set", (V)"bbb");
                operations.opsForValue().increment((K)"counter", 1L);

                try {
                    Thread.sleep(10_000L);
                } catch (InterruptedException ignore) {
                }

                operations.opsForValue().increment((K)"counter", 1L);
                operations.opsForValue().increment((K)"counter", 1L);

                return operations.exec();
            }
        });
        txResults.forEach(System.out::println);

        System.out.println(redisTemplate.opsForSet().pop("my_set"));
        System.out.println(redisTemplate.opsForValue().get("counter"));
    }
}

