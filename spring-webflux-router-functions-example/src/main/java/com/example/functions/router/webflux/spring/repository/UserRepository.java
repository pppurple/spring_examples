package com.example.functions.router.webflux.spring.repository;

import com.example.functions.router.webflux.spring.model.User;
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
