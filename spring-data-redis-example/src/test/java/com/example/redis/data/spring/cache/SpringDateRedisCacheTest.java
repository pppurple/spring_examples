package com.example.redis.data.spring.cache;

import com.example.redis.data.spring.cache.PersonService.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataRedisCacheConfig.class)
public class SpringDateRedisCacheTest {
    @Autowired
    private PersonService personService;

    @Before
    public void before() {
        personService.evict("Person:andy");
        personService.evict("ExpirePerson:bobby");
    }

    @Test
    public void cacheTest() throws InterruptedException {
        // キャッシュなし
        long start = System.currentTimeMillis();
        Person andy = personService.createPerson("andy");
        long end = System.currentTimeMillis();
        System.out.println("no cache [" + (end - start) + "msec]");

        // キャッシュヒット
        long start2 = System.currentTimeMillis();
        Person cachedAndy = personService.createPerson("andy");
        long end2 = System.currentTimeMillis();
        System.out.println("cache hit [" + (end2 - start2) + "msec]");

        assertThat(andy.getName()).isEqualTo(cachedAndy.getName());
        assertThat(andy.getRandomValue()).isEqualTo(cachedAndy.getRandomValue());
    /*
    no cache [3168msec]
    cache hit [34msec]
     */

    /*
        redis-cli
        > get "Person:andy"
        "{\"name\":\"andy\",\"randomValue\":-1896696146}"
     */
    }

    @Test
    public void cacheWithExpireTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        Person bobby = personService.createPersonWithExpire("bobby");
        long end = System.currentTimeMillis();
        System.out.println("no cache [" + (end - start) + "msec]");

        /*
        > get "ExpirePerson:bobby"
        "{\"name\":\"bobby\",\"randomValue\":1551354967}"
        しばらくして
        > get "ExpirePerson:bobby"
        (nil)
         */
    }

    @Test
    public void evictTest() throws InterruptedException {
        Person cindy = personService.createPerson("cindy");

        Thread.sleep(5_000L);

        personService.evict("Person:cindy");
    }
}
