package com.example.mockito.service;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class PeopleService {
    @Autowired
    private RestTemplate restTemplate;

    static private final String HOST = "http://dummy-host.com:8080";

    public People getPeople(String country) {
        String url = HOST + "/api/people/" + country;
        return restTemplate.getForObject(url, People.class);
    }

    public int postPeople(People people) {
        String url = HOST + "/api/people";
        return restTemplate.postForObject(url, people, Integer.class);
    }

    public int putPeople(People people) throws URISyntaxException {
        String url = HOST + "/api/people";

        URI uri = new URI(url);
        RequestEntity<People> requestEntity = RequestEntity
                .put(uri)
                .body(people);

        ResponseEntity<Integer> result = restTemplate.exchange(requestEntity, Integer.class);
        return result.getBody();
    }

    public int deletePeople(String country) {
        String url = HOST + "/api/people/" + country;

        ResponseEntity<Integer> result = restTemplate.exchange(url,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Integer.class,
                country);

        return result.getBody();
    }

    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class People {
        String country;
        int year;
        int population;
    }
}
