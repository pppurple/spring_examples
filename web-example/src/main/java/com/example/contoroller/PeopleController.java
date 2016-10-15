package com.example.contoroller;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PeopleController {
    @ResponseBody
    @RequestMapping(value = "/api/people", method = RequestMethod.GET)
    public People getPeople() {
        People people = new People();
        people.setCountry("Japan");
        people.setYear(2001);
        people.setPopulation(1_000_000);
        return people;
    }

    @ResponseBody
    @RequestMapping(value = "/api/people", method = RequestMethod.POST)
    public int postPeople(@RequestBody People people) {
        // 登録処理
        // 登録件数を返す
        return 1;
    }

    @ResponseBody
    @RequestMapping(value = "/api/people", method = RequestMethod.PUT)
    public int putPeople(@RequestBody People people) {
        // 更新処理
        // 更新件数を返す
        return 2;
    }

    @ResponseBody
    @RequestMapping(value = "/api/people/{country}", method = RequestMethod.DELETE)
    public int deletePeople(@PathVariable String country) throws Exception {
        // 削除処理
        // 削除件数を返す
        return 3;
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

