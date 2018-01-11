package com.example.webflux.spring.springwebfluxexample.controller;

import com.example.webflux.spring.springwebfluxexample.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class WebfluxUserController {
    private final UserRepository repository;

    public WebfluxUserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users/{id}")
    public Mono<User> getById(@PathVariable int id) {
        return repository.getById(id);
    }

    @GetMapping("/users")
    public Flux<User> getAll() {
        return repository.getAll();
    }

    @PostMapping("/users")
    Mono<Void> save(@RequestBody Mono<User> user) {
        return repository.save(user);
    }

    @PostMapping("/users/bulk")
    Mono<Void> saveBulk(@RequestBody Flux<User> users) {
        return repository.saveAll(users);
    }

    @PutMapping("/users/{id}")
    Mono<User> update(@PathVariable int id, @RequestBody Mono<User> user) {
        return repository.update(id, user);
    }

    @DeleteMapping("/users/{id}")
    Mono<Void> remove(@PathVariable int id) {
        return repository.delete(id);
    }

    @GetMapping("/users/bp")
    public Flux<User> getAllWithBackPressure() {
        return repository.getAll();
    }

    @AllArgsConstructor
    @Data
    public static class User {
        long id;
        String name;
        int age;
    }
}
