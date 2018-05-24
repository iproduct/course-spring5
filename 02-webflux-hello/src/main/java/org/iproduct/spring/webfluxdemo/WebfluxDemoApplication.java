package org.iproduct.spring.webfluxdemo;

import org.iproduct.spring.webfluxdemo.reactive.ReactiveHello;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@SpringBootApplication
public class WebfluxDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxDemoApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> routes (ReactiveHello handler) {
		return RouterFunctions.route(GET("/reactive-hello"), handler::sayHelloReactive);

	}
}
