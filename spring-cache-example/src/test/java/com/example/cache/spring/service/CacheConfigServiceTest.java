package com.example.cache.spring.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Supplier;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheConfigServiceTest {
    @Autowired
    private CacheConfigService cacheConfigService;

    @Test
    public void cacheConfigTest() throws Exception {
        // キャッシュなし
        time(() -> cacheConfigService.get("aaa"));
        Thread.sleep(3_000);

        // キャッシュ更新
        time(() -> cacheConfigService.put("aaa"));
        Thread.sleep(3_000);

        // キャッシュヒット
        time(() -> cacheConfigService.get("aaa"));
        Thread.sleep(3_000);

        // キャッシュ削除
        cacheConfigService.delete("aaa");
        Thread.sleep(3_000);

        // キャッシュミス
        time(() -> cacheConfigService.get("aaa"));
        Thread.sleep(3_000);
    }

    private void time(Supplier supplier) {
        long start = System.currentTimeMillis();
        System.out.print(supplier.get());
        long end = System.currentTimeMillis();
        System.out.println(" [" + (end - start) + "msec]");
    }
}