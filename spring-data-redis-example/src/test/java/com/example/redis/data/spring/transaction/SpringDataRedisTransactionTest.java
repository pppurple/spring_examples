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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataRedisTransactionConfig.class)
public class SpringDataRedisTransactionTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Before
    public void setup() {
        redisTemplate.delete("my_key");
        redisTemplate.delete("counter");
    }

    @Test
    public void multiExecTest() throws InterruptedException {
        // transaction start
        redisTemplate.multi();

        redisTemplate.opsForValue().set("my_key", "my_value");
        redisTemplate.opsForValue().increment("counter", 1L);
        redisTemplate.opsForValue().increment("counter", 1L);

        Thread.sleep(10_000L);

        redisTemplate.opsForValue().increment("counter", 1L);

        List<Object> results = redisTemplate.exec();
        // transaction end

        System.out.println(redisTemplate.opsForValue().get("my_key"));
        System.out.println(redisTemplate.opsForValue().get("counter"));

        System.out.println("-----------------");
        results.forEach(System.out::println);
        System.out.println("-----------------");
    }

    @Test
    public void discardTest() {
        // transaction start
        redisTemplate.multi();

        redisTemplate.opsForValue().set("my_key", "my_value");
        redisTemplate.opsForValue().increment("counter", 1L);
        redisTemplate.opsForValue().increment("counter", 1L);
        redisTemplate.opsForValue().increment("counter", 1L);

        redisTemplate.discard();
        // transaction end

        System.out.println(redisTemplate.opsForValue().get("my_key"));
        System.out.println(redisTemplate.opsForValue().get("counter"));
    }

    // https://www.javacodegeeks.com/2015/09/spring-data-and-redis.html#TransactionsRedis
    @Test
    public void useSessionCallbackTest() {
        List<Object> txResults = redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            @SuppressWarnings("unchecked")
            public <K, V> List<Object> execute(RedisOperations<K, V> operations) throws DataAccessException {
                operations.multi();

                operations.opsForValue().set((K)"my_key", (V)"my_value");
                operations.opsForValue().increment((K)"counter", 1L);
                operations.opsForValue().increment((K)"counter", 1L);

                try {
                    Thread.sleep(10_000L);
                } catch (InterruptedException ignore) {
                }

                operations.opsForValue().increment((K)"counter", 1L);

                return operations.exec();
            }
        });
        System.out.println(redisTemplate.opsForValue().get("my_key"));
        System.out.println(redisTemplate.opsForValue().get("counter"));

        System.out.println("-----------------");
        txResults.forEach(System.out::println);
        System.out.println("-----------------");
    }

    @BeforeTransaction
    public void before() {
        redisTemplate.delete("counter");
        System.out.println(redisTemplate.opsForValue().get("counter"));
    }

    @AfterTransaction
    public void after() {
        System.out.println(redisTemplate.opsForValue().get("counter"));
    }

    @Transactional
    @Rollback(false)
    @Test
    public void transactionalTest() throws InterruptedException {
        redisTemplate.opsForValue().increment("counter", 1);
        redisTemplate.opsForValue().increment("counter", 1);

        Thread.sleep(10_000L);

        redisTemplate.opsForValue().increment("counter", 1);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void rollbackTest() throws InterruptedException {
        redisTemplate.opsForValue().increment("counter", 1);
        redisTemplate.opsForValue().increment("counter", 1);

        Thread.sleep(10_000L);

        redisTemplate.opsForValue().increment("counter", 1);
    }
}

