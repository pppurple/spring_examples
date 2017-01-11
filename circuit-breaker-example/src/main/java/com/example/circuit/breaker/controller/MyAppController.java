package com.example.circuit.breaker.controller;

import com.example.circuit.breaker.service.MyAppService;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableCircuitBreaker
@RestController
public class MyAppController {
    private final MyAppService myAppService;

    public MyAppController(MyAppService myAppService) {
        this.myAppService = myAppService;
    }

    @RequestMapping(value = "myapp", method = RequestMethod.GET)
    public String myApp() {
        return myAppService.getSupplierText();
    }

    @RequestMapping(value = "myapp_with_threshold", method = RequestMethod.GET)
    public String myAppWithConfig() {
        return myAppService.getSupplierTextWithThreshold();
    }

    @RequestMapping(value = "myapp_with_sleep_window", method = RequestMethod.GET)
    public String myAppWithSleepWindow() {
        return myAppService.getSupplierTextWithSleepWindow();
    }

    @RequestMapping(value = "myapp_with_percentage", method = RequestMethod.GET)
    public String myAppWithPercentage() {
        return myAppService.getSupplierTextWithPercentage();
    }

    // not apply circuit breaker
    /*
    @RequestMapping(value = "myapp", method = RequestMethod.GET)
    public String myApp() {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create("http://localhost:8888/supplier/text");

        return restTemplate.getForObject(uri, String.class);
    }
    */
}
