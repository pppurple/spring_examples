package com.example.contoroller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.contoroller.PeopleController.People;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PeopleControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void getPeopleTest() {
        String url = "http://localhost:" + port + "/api/people";
        People people = testRestTemplate.getForObject(url, People.class);

        People expected = new People();
        expected.setCountry("Japan");
        expected.setYear(2001);
        expected.setPopulation(1_000_000);

        assertThat(people).isEqualTo(expected);
    }

    @Test
    public void postPeopleTest() {
        String url = "http://localhost:" + port + "/api/people";
        People people = new People();
        people.setCountry("America");
        people.setYear(2002);
        people.setPopulation(2_000_000);

        int result = testRestTemplate.postForObject(url, people, Integer.class);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void putPeopleTest() throws URISyntaxException {
        String url = "http://localhost:" + port + "/api/people";
        People people = new People();
        people.setCountry("America");
        people.setYear(2002);
        people.setPopulation(2_000_000);

        // put
        testRestTemplate.put(url, people);

        // exchange
        URI uri = new URI(url);
        RequestEntity<People> requestEntity = RequestEntity
                .put(uri)
                .body(people);

        ResponseEntity<Integer> result = testRestTemplate.exchange(requestEntity, Integer.class);

        assertThat(result.getBody()).isEqualTo(2);
    }

    @Test
    public void deletePeopleTest() {
        String url = "http://localhost:" + port + "/api/people/{country}";

        String country = "america";

        // delete
        testRestTemplate.delete(url, country);

        // exchange
        ResponseEntity<Integer> result = testRestTemplate.exchange(url,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Integer.class,
                country);

        assertThat(result.getBody()).isEqualTo(3);
    }
}