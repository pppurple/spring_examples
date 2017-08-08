package com.example.propagation.transaction.transactionpropagationexample.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class MyServiceA {
    private final JdbcTemplate jdbc;
    private final DataSourceTransactionManager transactionManager;

    public MyServiceA(JdbcTemplate jdbc, DataSourceTransactionManager transactionManager) {
        this.jdbc = jdbc;
        this.transactionManager = transactionManager;
    }

    public int select() {
        List<Map<String, Object>> list = jdbc.queryForList("SELECT * FROM person");
        list.forEach(System.out::println);
        return list.size();
    }

    public void nonTransaction() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void required() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNew() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void supports() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void notSupported() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatory() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void nested() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21);
    }

    @Transactional(propagation = Propagation.NEVER)
    public void never() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21);
    }

    public void initDB() {
        jdbc.execute("DROP TABLE IF EXISTS person");
        jdbc.execute("CREATE TABLE person (id INTEGER NOT NULL IDENTITY, name VARCHAR(256), age INTEGER)"
        );
        jdbc.update("INSERT INTO person (name, age) VALUES (?, ?)", "Andy", 19);
    }
}
