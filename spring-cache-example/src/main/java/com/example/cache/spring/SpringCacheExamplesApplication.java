package com.example.cache.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringCacheExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCacheExamplesApplication.class, args);
	}
}
