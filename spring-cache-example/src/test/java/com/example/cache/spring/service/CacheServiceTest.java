package com.example.cache.spring.service;

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
        // getString 1回目キャッシュなし
        time(() -> cacheService.getString());
        Thread.sleep(3_000);

        // getString 2回目キャッシュヒット
        time(() -> cacheService.getString());
        Thread.sleep(3_000);

        // AnotherString 1回目キャッシュヒット
        time(() -> cacheService.getAnotherString());
    }

    @Test
    public void cacheWithArgTest() throws Exception {
        // 1回目キャッシュなし
        time(() -> cacheService.getStringWithArg("aaa"));
        Thread.sleep(3_000);

        // 1回目キャッシュなし
        time(() -> cacheService.getStringWithArg("bbb"));
        Thread.sleep(3_000);

        // 2回目キャッシュヒット
        time(() -> cacheService.getStringWithArg("aaa"));
    }

    @Test
    public void cacheWithArgsTest() throws Exception {
        // キャッシュなし
        time(() -> cacheService.getStringWithArgs("aaa", 111, true));
        Thread.sleep(3_000);

        // キャッシュなし
        time(() -> cacheService.getStringWithArgs("aaa", 111, false));
        Thread.sleep(3_000);

        // キャッシュなし
        time(() -> cacheService.getStringWithArgs("bbb", 111, true));
        Thread.sleep(3_000);

        // キャッシュヒット
        time(() -> cacheService.getStringWithArgs("aaa", 111, true));
    }

    @Test
    public void cacheWithArgsAndKeyTest() throws Exception {
        // キャッシュなし
        time(() -> cacheService.getStringWithArgsAndKey("aaa", 111, true));
        Thread.sleep(3_000);

        // キャッシュなし
        time(() -> cacheService.getStringWithArgsAndKey("bbb", 111, true));
        Thread.sleep(3_000);

        // キャッシュヒット
        time(() -> cacheService.getStringWithArgsAndKey("aaa", 222, false));
    }

    @Test
    public void putTest() throws Exception {
        // キャッシュなし
        time(() -> cacheService.getStringWithArg("aaa"));
        Thread.sleep(3_000);

        // キャッシュ更新
        time(() -> cacheService.put("aaa", "newVal"));
        Thread.sleep(3_000);

        // キャッシュヒット
        time(() -> cacheService.getStringWithArg("aaa"));
    }

    @Test
    public void evictTest() throws Exception {
        // キャッシュなし
        time(() -> cacheService.getStringWithArg("aaa"));
        time(() -> cacheService.getStringWithArg("bbb"));
        Thread.sleep(3_000);

        // キャッシュ削除
        cacheService.evict("aaa");
        Thread.sleep(3_000);

        // キャッシュなし
        time(() -> cacheService.getStringWithArg("aaa"));
        // キャッシュヒット
        time(() -> cacheService.getStringWithArg("bbb"));
    }

    @Test
    public void evictAllTest() throws Exception {
        // キャッシュなし
        time(() -> cacheService.getStringWithArg("aaa"));
        time(() -> cacheService.getStringWithArg("bbb"));
        Thread.sleep(3_000);

        // キャッシュ削除
        cacheService.evictAll("aaa");
        Thread.sleep(3_000);

        // キャッシュなし
        time(() -> cacheService.getStringWithArg("aaa"));
        time(() -> cacheService.getStringWithArg("bbb"));
    }

    @Test
    public void cachingTest() throws Exception {
        // キャッシュなし
        time(() -> cacheService.getStringWithArg("aaa"));
        time(() -> cacheService.getAnotherStringWithArg("aaa"));
        Thread.sleep(3_000);

        // キャッシュ削除
        cacheService.caching("aaa");
        Thread.sleep(3_000);

        // キャッシュなし
        time(() -> cacheService.getStringWithArg("aaa"));
        time(() -> cacheService.getAnotherStringWithArg("aaa"));
    }

    private void time(Supplier supplier) {
        long start = System.currentTimeMillis();
        System.out.print(supplier.get());
        long end = System.currentTimeMillis();
        System.out.println(" [" + (end - start) + "msec]");
    }
}