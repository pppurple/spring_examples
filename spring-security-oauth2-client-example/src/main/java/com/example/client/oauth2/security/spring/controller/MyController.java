package com.example.client.oauth2.security.spring.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MyController {
    @Autowired
    OAuth2RestTemplate oAuth2RestTemplate;

    @RequestMapping("/github")
    public Repo[] github() {
        OAuth2AccessToken accessToken = oAuth2RestTemplate.getAccessToken();
        ResponseEntity<Repo[]> responseEntity = oAuth2RestTemplate
                .getForEntity("https://api.github.com/user/repos?access_token=" + accessToken, Repo[].class);

        return responseEntity.getBody();
    }

    @RequestMapping("/twitter")
    public Twit[] twitter() {

        return null;
    }

    @RequestMapping("/sales")
    public String sale() {
        OAuth2AccessToken accessToken = oAuth2RestTemplate.getAccessToken();

        return accessToken.getValue();
    }

    @Data
    public static class Repo {
        String name;
        String description;
    }

    @Data
    public static class Twit {

    }
}
