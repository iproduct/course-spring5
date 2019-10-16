package org.iproduct.webflux;

import org.iproduct.webflux.web.ReactiveArticlesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunction.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class WebfluxDemoLab7Application {

	@Autowired
	ReactiveArticlesHandler handler;

	public static void main(String[] args) {
		SpringApplication.run(WebfluxDemoLab7Application.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> router() {
		return route(GET("/api/articles"), handler::getAllArticles)
			.andRoute(POST("/api/articles"), handler::addArticle)
			.andRoute(GET("/api/articles/{id}"), handler::getArticleById)
			.andRoute(DELETE("/api/articles/{id}"), handler::deleteArticleById);
	}

}
