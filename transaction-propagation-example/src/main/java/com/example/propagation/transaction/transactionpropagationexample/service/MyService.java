package com.example.propagation.transaction.transactionpropagationexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MyService {
    private JdbcTemplate jdbc;

    @Autowired
    public MyService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    public int update() {
        initDB();

        List<Map<String, Object>> list = jdbc.queryForList("SELECT * FROM person");
        list.forEach(System.out::println);
        return list.size();
    }

    private void initDB() {
        jdbc.execute("DROP TABLE IF EXISTS person");
        jdbc.execute("CREATE TABLE person (id INTEGER NOT NULL IDENTITY, name VARCHAR(256), age INTEGER)"
        );
        jdbc.update("INSERT INTO person (name, age) VALUES (?, ?)", "Andy", 20);
    }
}
