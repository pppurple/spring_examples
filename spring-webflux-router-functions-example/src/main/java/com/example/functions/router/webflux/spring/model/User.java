package com.example.functions.router.webflux.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {
    long id;
    String name;
    int age;
}
