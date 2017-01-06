package com.example.cercuit.breaker.supplier.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupplierController {
    @RequestMapping(value = "supplier/text", method = RequestMethod.GET)
    public String replyTest() {
        return "Reply supplier text";
    }
}
