package com.example.cache.spring.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Supplier;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheJsr107ServiceTest {
    @Autowired
    private CacheJsr107Service cacheJsr107Service;

    @Ignore
    @Test
    public void getTest() throws Exception {
        // 1回目キャッシュなし
        time(() -> cacheJsr107Service.get("aaa"));
        Thread.sleep(3_000);

        // 2回目キャッシュヒット
        time(() -> cacheJsr107Service.get("aaa"));
    }

    @Ignore
    @Test
    public void putTest() throws Exception {
        // キャッシュなし
        time(() -> cacheJsr107Service.get("aaa"));
        Thread.sleep(3_000);

        // キャッシュ更新
        time(() -> cacheJsr107Service.put("aaa", "newVal"));
        Thread.sleep(3_000);

        // キャッシュヒット
        time(() -> cacheJsr107Service.get("aaa"));
    }

    @Ignore
    @Test
    public void removeTest() throws Exception {
        // キャッシュなし
        time(() -> cacheJsr107Service.get("aaa"));
        time(() -> cacheJsr107Service.get("bbb"));
        Thread.sleep(3_000);

        // キャッシュ削除
        cacheJsr107Service.remove("aaa");
        Thread.sleep(3_000);

        // キャッシュなし
        time(() -> cacheJsr107Service.get("aaa"));
        // キャッシュヒット
        time(() -> cacheJsr107Service.get("bbb"));
    }

    @Test
    public void removeAllTest() throws Exception {
        // キャッシュなし
        time(() -> cacheJsr107Service.get("aaa"));
        time(() -> cacheJsr107Service.get("bbb"));
        Thread.sleep(3_000);

        // キャッシュ削除
        cacheJsr107Service.removeAll();
        Thread.sleep(3_000);

        // キャッシュなし
        time(() -> cacheJsr107Service.get("aaa"));
        time(() -> cacheJsr107Service.get("bbb"));
    }

    private void time(Supplier supplier) {
        long start = System.currentTimeMillis();
        System.out.print(supplier.get());
        long end = System.currentTimeMillis();
        System.out.println(" [" + (end - start) + "msec]");
    }
}