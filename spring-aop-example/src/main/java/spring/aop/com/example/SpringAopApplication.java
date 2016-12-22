package spring.aop.com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spring.aop.com.example.service.SampleService;

@SpringBootApplication
public class SpringAopApplication implements CommandLineRunner {
    @Autowired
    private SampleService sampleService;

    @Override
    public void run(String... args) throws Exception {
        sampleService.getRandomValue(100);
//        sampleService.getLength(null);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringAopApplication.class, args);
    }
}
