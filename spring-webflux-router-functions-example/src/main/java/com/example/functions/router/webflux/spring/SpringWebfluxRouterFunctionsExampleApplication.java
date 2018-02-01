package com.example.functions.router.webflux.spring;

import com.example.functions.router.webflux.spring.component.UserHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class SpringWebfluxRouterFunctionsExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxRouterFunctionsExampleApplication.class, args);
    }

    /*
    @Bean
    public RouterFunction<ServerResponse> routes() {
        return route(GET("/"), req -> ServerResponse.ok()
                .body(Flux.just("Hello", "Router", "Functions!"), String.class)
        );
    }
    */

    /*
    @Bean
    public RouterFunction<ServerResponse> routesHello(UserHandler userHandler) {
        return route(GET("/"), userHandler::hello);
    }
    */

    @Bean
    public RouterFunction<ServerResponse> routes(UserHandler userHandler) {
        return route(GET("/users/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::get)
                .andRoute(GET("/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::getAll)
                .andRoute(POST("/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::save)
                .andRoute(POST("/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::saveAll)
                .andRoute(PUT("/users/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::update)
                .andRoute(DELETE("/users/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::delete);
    }

    /*
    @Bean
    public RouterFunction<ServerResponse> routesNest(UserHandler userHandler) {
        return nest(path("users"), route(GET("/{id}"), userHandler::get)
                .andRoute(GET("/").and(accept(MediaType.APPLICATION_JSON)), userHandler::getAll)
                .andRoute(POST("/").and(accept(MediaType.APPLICATION_JSON)), userHandler::save)
                .andRoute(POST("/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::saveAll)
                .andRoute(PUT("/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::update)
                .andRoute(DELETE("/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::delete));
    }
    */
}
