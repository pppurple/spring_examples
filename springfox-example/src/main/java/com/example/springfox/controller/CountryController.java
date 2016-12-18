package com.example.springfox.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class CountryController {
    @ResponseBody
    @RequestMapping(value = "/api/country/now", method = RequestMethod.GET)
    public Date getDate() {
        return new Date();
    }

    @ResponseBody
    @RequestMapping(value = "/api/country/{countryName}/{cityName}", method = RequestMethod.GET)
    public MyResponseEntityWithStatus<City> getCity(@PathVariable String country, @PathVariable String city) {
        City yokohama = new City("yokohama", 100);
        HttpStatus status = HttpStatus.OK;
        return new MyResponseEntityWithStatus<>(yokohama, status);
    }

    @ResponseBody
    @RequestMapping(value = "/api/country/{countryName}", method = RequestMethod.GET)
    public MyResponseEntity<List<Country>> getCountry(@PathVariable String country) {
        Country america = new Country("America", "English", 1_000_000_000);
        Country japan = new Country("Japan", "japanese", 1_000_000);
        List<Country> countries = Arrays.asList(america, japan);
        return new MyResponseEntity<>(countries);
    }

    @ApiIgnore(value = "this is dummy")
    @ResponseBody
    @RequestMapping(value = "/api/country/dummy", method = RequestMethod.GET)
    public String dummy() {
        return "dummy country";
    }

    @Data
    @AllArgsConstructor
    public static class Country {
        String name;
        String language;
        int population;
    }

    @Data
    @AllArgsConstructor
    public static class City {
        String name;
        int population;
    }

    @Data
    @AllArgsConstructor
    public static class MyResponseEntity<T> {
        T res;
    }

    @Data
    @AllArgsConstructor
    public static class MyResponseEntityWithStatus<T> {
        T res;
        HttpStatus httpStatus;
    }
}
