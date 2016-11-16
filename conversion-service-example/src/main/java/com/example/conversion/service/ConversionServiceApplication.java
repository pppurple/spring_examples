package com.example.conversion.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@SpringBootApplication
public class ConversionServiceApplication {
    @Bean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConversionServiceApplication.class, args);
    }
}
