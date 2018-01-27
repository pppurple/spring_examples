package com.example.webflux.spring.springwebfluxexample.repository;

import com.example.webflux.spring.springwebfluxexample.controller.WebfluxUserController.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> getById(long id);
    Flux<User> getAll();
    Mono<Void> save(Mono<User> user);
    Mono<Void> saveAll(Flux<User> users);
    Mono<User> update(long id, Mono<User> user);
    Mono<Void> delete(long id);
}
