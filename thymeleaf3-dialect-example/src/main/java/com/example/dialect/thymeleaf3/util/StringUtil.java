package com.example.dialect.thymeleaf3.util;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {
    public String addPrefix(String pre, String str) {
        return pre + str;
    }
}
