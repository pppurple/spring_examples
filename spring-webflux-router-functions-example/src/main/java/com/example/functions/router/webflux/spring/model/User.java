package com.example.functions.router.webflux.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {
    private long id;
    private String name;
    private int age;
}
