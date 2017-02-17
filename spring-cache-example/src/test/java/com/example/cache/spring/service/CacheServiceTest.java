package com.example.cache.spring.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheServiceTest {
    @Autowired
    private CacheService cacheService;

    @Before
    public void before() {
    }

    @Test
    public void myCacheTest() throws Exception {
        // 1回目キャッシュなし
        time(() -> cacheService.getString());

        Thread.sleep(3_000);

        // 2回目キャッシュヒット
        time(() -> cacheService.getString());
    }

    @Test
    public void anotherCacheTest() throws Exception {
        // 1回目キャッシュなし
        time(() -> cacheService.getString());

        Thread.sleep(3_000);

        // 2回目キャッシュヒット
        time(() -> cacheService.getString());

        Thread.sleep(3_000);

        // anotherCache 1回目
        time(() -> cacheService.getAnotherString());
    }

    private void time(Supplier supplier) {
        long start = System.currentTimeMillis();
        System.out.print(supplier.get());
        long end = System.currentTimeMillis();
        System.out.println("[" + (end - start) + "msec]");
    }

}