package com.example.cercuit.breaker;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CercuitBreakerExampleApplicationTests {
    private TestRestTemplate restTemplate;

    @Ignore
    @Test
    public void withThreshold() throws InterruptedException {
        restTemplate = new TestRestTemplate();
        URI uri = URI.create("http://localhost:8080/myapp_with_config");

        for(int i = 0; i < 7; i++) {
            Thread.sleep(1000);
            restTemplate.getForObject(uri, String.class);
        }
    }

    @Ignore
    @Test
    public void withSleepWindow() throws InterruptedException {
        restTemplate = new TestRestTemplate();
        URI uri = URI.create("http://localhost:8080/myapp_with_sleep_window");

        for(int i = 0; i < 11; i++) {
            Thread.sleep(1000);
            restTemplate.getForObject(uri, String.class);
        }
    }

    @Test
    public void withPercentage() throws InterruptedException {
        restTemplate = new TestRestTemplate();
        URI uri = URI.create("http://localhost:8080/myapp_with_percentage");

        for(int i = 0; i < 10; i++) {
            Thread.sleep(2000);
            String text = restTemplate.getForObject(uri, String.class);
            System.out.println(i + " : " + text);
        }
    }
}
