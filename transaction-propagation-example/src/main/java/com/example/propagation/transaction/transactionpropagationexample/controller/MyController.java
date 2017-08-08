package com.example.propagation.transaction.transactionpropagationexample.controller;

import com.example.propagation.transaction.transactionpropagationexample.service.MyServiceA;
import com.example.propagation.transaction.transactionpropagationexample.service.MyServiceB;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    private final MyServiceA myServiceA;
    private final MyServiceB myServiceB;

    public MyController(MyServiceA myServiceA, MyServiceB myServiceB) {
        this.myServiceA = myServiceA;
        this.myServiceB = myServiceB;
    }

    @RequestMapping("/required")
    public void required() {
        myServiceA.initDB();
        myServiceA.required();
    }

    @RequestMapping("/required_from_b")
    public void requiredFromB() {
        myServiceB.initDB();
        myServiceB.required();
    }

    @RequestMapping("/requires_new")
    public void requiresNew() {
        myServiceA.initDB();
        myServiceA.requiresNew();
    }

    @RequestMapping("/requires_new_from_b")
    public void requiresNewFromB() {
        myServiceB.initDB();
        myServiceB.requiresNew();
    }

    @RequestMapping("/supports")
    public void supports() {
        myServiceA.initDB();
        myServiceA.supports();
    }

    @RequestMapping("/supports_form_b")
    public void supportsFromB() {
        myServiceB.initDB();
        myServiceB.supports();
    }

    @RequestMapping("/not_supported")
    public void notSupported() {
        myServiceA.initDB();
        myServiceA.notSupported();
    }

    @RequestMapping("/not_supported_from_b")
    public void notSupportedFromB() {
        myServiceB.initDB();
        myServiceB.notSupported();
    }

    @RequestMapping("/mandatory")
    public void mandatory() {
        myServiceA.initDB();
        myServiceA.mandatory();
    }

    @RequestMapping("/mandatory_from_b")
    public void mandatoryFromB() {
        myServiceB.initDB();
        myServiceB.mandatory();
    }

    @RequestMapping("/nested")
    public void nested() {
        myServiceA.initDB();
        myServiceA.nested();
    }

    @RequestMapping("/nested_from_b")
    public void nestedFromB() {
        myServiceB.initDB();
        myServiceB.nested();
    }

    @RequestMapping("/never")
    public void never() {
        myServiceA.initDB();
        myServiceA.never();
    }

    @RequestMapping("/never_from_b")
    public void neverFromB() {
        myServiceB.initDB();
        myServiceB.never();
    }
}
