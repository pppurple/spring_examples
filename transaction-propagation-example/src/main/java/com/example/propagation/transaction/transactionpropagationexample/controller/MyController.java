package com.example.propagation.transaction.transactionpropagationexample.controller;

import com.example.propagation.transaction.transactionpropagationexample.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @Autowired
    private MyService myService;

    @RequestMapping("/my")
    public void update() {
        myService.update();
    }
}
