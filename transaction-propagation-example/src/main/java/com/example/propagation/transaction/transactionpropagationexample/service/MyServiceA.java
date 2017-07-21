package com.example.propagation.transaction.transactionpropagationexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Map;

@Service
public class MyServiceA {
    private JdbcTemplate jdbc;
    private DataSourceTransactionManager transactionManager;

    @Autowired
    public MyServiceA(JdbcTemplate jdbc, DataSourceTransactionManager transactionManager) {
        this.jdbc = jdbc;
        this.transactionManager = transactionManager;
    }

    public int select() {
        initDB();

        List<Map<String, Object>> list = jdbc.queryForList("SELECT * FROM person");
        list.forEach(System.out::println);
        return list.size();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRequired() {
        initDB();

        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21);
        List<Map<String, Object>> list = jdbc.queryForList("SELECT * FROM person");

        list.forEach(System.out::println);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void updateSupports() {
        initDB();

        jdbc.update("INSERT INTO person (name, age) VALUES (?, ?)", "Andy", 20);
    }

    private void initDB() {
        jdbc.execute("DROP TABLE IF EXISTS person");
        jdbc.execute("CREATE TABLE person (id INTEGER NOT NULL IDENTITY, name VARCHAR(256), age INTEGER)"
        );
        jdbc.update("INSERT INTO person (name, age) VALUES (?, ?)", "Andy", 20);
    }
}
