package com.example.functions.router.webflux.spring.springwebfluxrouterfunctionsexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
}
