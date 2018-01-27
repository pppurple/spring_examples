package com.example.webflux.spring.springwebfluxexample.repository;

import com.example.webflux.spring.springwebfluxexample.controller.WebfluxUserController.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private Map<Long, User> users = new HashMap<>();

    @PostConstruct
    public void init() {
        users.put(1L, new User(1, "Alice", 20));
        users.put(2L, new User(2, "Bobby", 32));
        users.put(3L, new User(3, "Cindy", 41));
    }

    @Override
    public Mono<User> getById(long id) {
        return Mono.justOrEmpty(users.get(id)).log();
        // justだと存在しない場合エラーになる。
        // return Mono.just(users.get(id)).log();
    }

    @Override
    public Flux<User> getAll() {
        return Flux.fromIterable(users.values()).log();
    }

    @Override
    public Mono<Void> save(Mono<User> user) {
        return user.map(u -> {
            System.out.println("saved:" + u.getId());
            users.put(u.getId(), u);
            return u;
        }).log().then();
        /*
        return user.doOnNext(u -> {
            System.out.println("saved:" + u.getId());
            users.put(u.getId(), u);
        }).log().then();
         */
    }

    @Override
    public Mono<Void> saveAll(Flux<User> users) {
        return users.map(u -> {
            System.out.println("saved:" + u.getId());
            this.users.put(u.getId(), u);
            return u;
        }).log().then();
        /*
        return users.doOnNext(u -> {
            System.out.println("saved:" + u.getId());
            this.users.put(u.getId(), u);
        }).log().then();
        */
    }

    @Override
    public Mono<User> update(long id, Mono<User> user) {
        return user.map(u -> users.put(id, u)).log();
        // return user.doOnNext(u -> users.put(id, u)).log();
    }

    @Override
    public Mono<Void> delete(long id) {
        users.remove(id);
        return Mono.empty();
    }
}

