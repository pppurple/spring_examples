package com.example.propagation.transaction.transactionpropagationexample.controller;

import com.example.propagation.transaction.transactionpropagationexample.service.MyService;
import com.example.propagation.transaction.transactionpropagationexample.service.MyServiceA;
import com.example.propagation.transaction.transactionpropagationexample.service.MyServiceB;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    private final MyServiceA myServiceA;
    private final MyServiceB myServiceB;
    private final MyService myService;

    public MyController(MyService myService, MyServiceA myServiceA, MyServiceB myServiceB) {
        this.myService = myService;
        this.myServiceA = myServiceA;
        this.myServiceB = myServiceB;
    }

    @RequestMapping("/required")
    public void required() {
        myServiceA.initDB();
        myServiceA.required();
    }

    @RequestMapping("/required_nested")
    public void requiredNested() {
        myServiceB.initDB();
        myServiceB.required();
    }

    @RequestMapping("/requires_new")
    public void requiresNew() {
        myServiceA.initDB();
        myServiceA.requiresNew();
    }

    @RequestMapping("/requires_new_nested")
    public void requiresNewNested() {
        myServiceB.initDB();
        myServiceB.requiresNew();
    }

    @RequestMapping("/supports")
    public void supports() {
        myServiceA.initDB();
        myServiceA.supports();
    }

    @RequestMapping("/supports_nested")
    public void supportsNested() {
        myServiceB.initDB();
        myServiceB.supports();
    }

    @RequestMapping("/not_supported")
    public void notSupported() {
        myServiceA.initDB();
        myServiceA.notSupported();
    }

    @RequestMapping("/not_supported_nested")
    public void notSupportedNested() {
        myServiceB.initDB();
        myServiceB.notSupported();
    }

    @RequestMapping("/mandatory")
    public void mandatory() {
        myServiceA.initDB();
        myServiceA.mandatory();
    }

    @RequestMapping("/mandatory_nested")
    public void mandatoryNested() {
        myServiceB.initDB();
        myServiceB.mandatory();
    }

    @RequestMapping("/nested")
    public void nested() {
        myServiceA.initDB();
        myServiceA.nested();
    }

    @RequestMapping("/nested_nested")
    public void nestedNested() {
        myServiceB.initDB();
        myServiceB.nested();
    }

    @RequestMapping("/never")
    public void never() {
        myServiceA.initDB();
        myServiceA.never();
    }

    @RequestMapping("/never_nested")
    public void neverNested() {
        myServiceB.initDB();
        myServiceB.never();
    }
}
