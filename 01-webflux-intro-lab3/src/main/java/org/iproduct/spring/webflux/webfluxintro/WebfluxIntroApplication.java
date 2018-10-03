package org.iproduct.spring.webflux.webfluxintro;

import org.iproduct.spring.webflux.webfluxintro.webreactive.ArticleHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@SpringBootApplication
public class WebfluxIntroApplication {
	@Bean
	public RouterFunction<ServerResponse> routes (ArticleHandler handler) {
		return RouterFunctions.route(GET("/api/articles"), handler::all);
	}


	public static void main(String[] args) {
		SpringApplication.run(WebfluxIntroApplication.class, args);
	}
}
