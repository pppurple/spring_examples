package spring.aop.com.example.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SampleService {
    public int getRandomValue(int seed) {
        Random random = new Random(seed);
        return random.nextInt();
    }

    public int getLength(String str) {
        return str.length();
    }
}
