package com.example.propagation.transaction.transactionpropagationexample.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyServiceB {
    private final JdbcTemplate jdbc;
    private final MyServiceA myServiceA;

    public MyServiceB(JdbcTemplate jdbc, MyServiceA myServiceA) {
        this.jdbc = jdbc;
        this.myServiceA = myServiceA;
    }

    // default
    @Transactional(propagation = Propagation.REQUIRED)
    public void required() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Bobby'", 20);
        myServiceA.required();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void requiresNew() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Bobby'", 20);
        myServiceA.requiresNew();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void supports() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Bobby'", 20);
        myServiceA.supports();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void notSupported() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Bobby'", 20);
        myServiceA.notSupported();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void mandatory() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Bobby'", 20);
        myServiceA.mandatory();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void nested() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Bobby'", 20);
        myServiceA.nested();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void never() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Bobby'", 20);
        myServiceA.never();
    }

    public void initDB() {
        jdbc.execute("DROP TABLE IF EXISTS person");
        jdbc.execute("CREATE TABLE person (id INTEGER NOT NULL IDENTITY, name VARCHAR(256), age INTEGER)"
        );
        jdbc.update("INSERT INTO person (name, age) VALUES (?, ?)", "Bobby", 19);
    }
}
