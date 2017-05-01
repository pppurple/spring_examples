package com.example.client.oauth2.security.spring.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class MyController {
    @Autowired
    OAuth2RestTemplate oAuth2RestTemplate;

    @RequestMapping("/my")
    @ResponseBody
    public String top() {
        OAuth2AccessToken accessToken = oAuth2RestTemplate.getAccessToken();

        RestTemplate restTemplate = new RestTemplate();
//        My forObject = restTemplate.getForObject("https://api.github.com/user?access_token=" + accessToken, My.class);
        ResponseEntity<Repo[]> responseEntity = restTemplate.getForEntity("https://api.github.com/user/repos?access_token=" + accessToken, Repo[].class);
        Repo[] repos = responseEntity.getBody();



        //return forObject.getLogin() + ":" + forObject.getId();
    }

    @Data
    public static class My {
        String login;
        String id;
    }

    @Data
    public static class Repo {
        String name;
    }
}
