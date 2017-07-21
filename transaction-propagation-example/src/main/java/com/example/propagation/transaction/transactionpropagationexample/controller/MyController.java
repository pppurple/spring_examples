package com.example.propagation.transaction.transactionpropagationexample.controller;

import com.example.propagation.transaction.transactionpropagationexample.service.MyServiceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @Autowired
    private MyServiceA myServiceA;

    @RequestMapping("/my")
    public void update() {
        myServiceA.updateRequired();
    }
}
