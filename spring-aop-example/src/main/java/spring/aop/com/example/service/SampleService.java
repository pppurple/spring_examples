package spring.aop.com.example.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public String readFile(Path path) throws IOException {
        String text = "";
        try(BufferedReader br = Files.newBufferedReader(path)) {
            text += br.readLine();
        }
        return text;
    }
}
