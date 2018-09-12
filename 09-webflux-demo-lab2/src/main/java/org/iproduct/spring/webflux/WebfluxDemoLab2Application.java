package org.iproduct.spring.webflux;

import org.iproduct.spring.webflux.webflux.ArticleHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class WebfluxDemoLab2Application {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxDemoLab2Application.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> routes(ArticleHandler articleHandler) {
		return route(GET("/api/articles"), articleHandler::all);
	}
}
