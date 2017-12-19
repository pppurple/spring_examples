package com.example.webflux.spring.springwebfluxexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@SpringBootApplication
public class SpringWebfluxExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxExampleApplication.class, args);
    }

    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(GET("/hello"), req -> ServerResponse
                .ok()
                .body(Flux.just("Hello", "Router", "Functions!"), String.class));
    }
}
