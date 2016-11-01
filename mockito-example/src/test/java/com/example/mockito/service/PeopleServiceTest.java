package com.example.mockito.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URISyntaxException;

import static com.example.mockito.service.PeopleService.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PeopleServiceTest {
    @InjectMocks
    PeopleService peopleService;

    private PeopleService mockService;

    @Before
    public void before() {
        mockService = mock(PeopleService.class);
    }

    @Test
    public void getPeopleTest() {
        String country = "japan";

        People expected = new People();
        expected.setCountry("japan");
        expected.setYear(2001);
        expected.setPopulation(1_000_000);

        when(mockService.getPeople(country)).thenReturn(expected);

        // assert
        assertThat(mockService.getPeople(country).getCountry()).isEqualTo("japan");
        assertThat(mockService.getPeople(country).getYear()).isEqualTo(2001);
        assertThat(mockService.getPeople(country).getPopulation()).isEqualTo(1_000_000);

        // verify
        verify(mockService, times(3)).getPeople(country);
        verify(mockService, never()).postPeople(new People());

        // 前置記法
        reset(mockService);
        doReturn(expected).when(mockService).getPeople(country);
        assertThat(mockService.getPeople(country).getCountry()).isEqualTo("japan");
        assertThat(mockService.getPeople(country).getYear()).isEqualTo(2001);
        assertThat(mockService.getPeople(country).getPopulation()).isEqualTo(1_000_000);
    }

    @Test
    public void postPeopleTest() {
        People people = new People();
        people.setCountry("japan");
        people.setYear(2001);
        people.setPopulation(1_000_000);

        when(mockService.postPeople(people)).thenReturn(1);

        assertThat(mockService.postPeople(people)).isEqualTo(1);
    }

    @Test
    public void putPeopleTest() throws URISyntaxException {
        People people = new People();
        people.setCountry("japan");
        people.setYear(2001);
        people.setPopulation(1_000_000);

        when(mockService.putPeople(people)).thenReturn(1);

        assertThat(mockService.putPeople(people)).isEqualTo(1);
    }

    @Test
    public void deletePeopleTest() {
        when(mockService.deletePeople("japan")).thenReturn(1);

        assertThat(mockService.deletePeople("japan")).isEqualTo(1);
    }
}
