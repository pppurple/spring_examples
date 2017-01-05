package com.example.cercuit.breaker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyAppController {
    @RequestMapping(value = "myapp")
    public String myApp() {
        return "my application";
    }
}
