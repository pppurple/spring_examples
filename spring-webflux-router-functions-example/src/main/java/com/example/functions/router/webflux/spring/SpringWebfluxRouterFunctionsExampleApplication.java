package com.example.functions.router.webflux.spring;

import com.example.functions.router.webflux.spring.component.UserHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@SpringBootApplication
public class SpringWebfluxRouterFunctionsExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxRouterFunctionsExampleApplication.class, args);
    }

/*    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(GET("/hello"), req -> ServerResponse
                .ok()
                .body(Flux.just("Hello", "Router", "Functions!"), String.class));
    }*/


    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route(GET("/"), req -> ServerResponse.ok()
                .body(Flux.just("Hello", "Router", "Functions!"), String.class)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> routes2(UserHandler userHandler) {
        return RouterFunctions.route(GET("/"), userHandler::hello);
    }
}
