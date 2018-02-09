package com.example.functions.router.webflux.spring.component;

import com.example.functions.router.webflux.spring.model.User;
import com.example.functions.router.webflux.spring.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class UserHandler {
    private final UserRepository repository;

    public UserHandler(UserRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> hello(ServerRequest req) {
        return ok().body(Flux.just("hello"), String.class);
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        long userId = Long.valueOf(request.pathVariable("id"));

        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        Mono<User> userMono = repository.getById(userId);

/*        return userMono.flatMap(user -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(user)))
                .switchIfEmpty(notFound);*/

/*        return repository.getById(userId)
                .flatMap(user -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(user)))
                .switchIfEmpty(ServerResponse.notFound().build());*/

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userMono, User.class);
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<User> all = repository.getAll();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(all, User.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        return ServerResponse.ok()
                .build(repository.save(userMono));
    }

    public Mono<ServerResponse> saveAll(ServerRequest request) {
        Flux<User> userFlux = request.bodyToFlux(User.class);
        return ServerResponse.ok()
                .build(repository.saveAll(userFlux));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        long userId = Long.valueOf(request.pathVariable("id"));

        Mono<User> userMono = request.bodyToMono(User.class);

        Mono<User> updatedMono = repository.update(userId, userMono);

		return updatedMono
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(user)));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        long userId = Long.valueOf(request.pathVariable("id"));

        return ServerResponse.ok()
                .build(repository.delete(userId));
    }
}