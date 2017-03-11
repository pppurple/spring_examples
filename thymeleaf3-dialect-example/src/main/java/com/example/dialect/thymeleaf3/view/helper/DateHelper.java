package com.example.dialect.thymeleaf3.view.helper;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateHelper {
    public Date now() {
        return new Date();
    }
}
